package com.lsitc.global.util;

import com.lsitc.global.common.share.vo.ApndFileVo;
import com.lsitc.global.error.exception.BisiExcp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
//@ConfigurationProperties(prefix="file")
public final class FileUtils extends org.apache.commons.io.FileUtils {

  @Value("${file.filePath}")
  public static String FILE_PATH = "tmp";
  @Value("${file.fileTmpPath}")
  public static String FILE_TMP_PATH = "tmp";
  @Value("${file.valid-extensions}")
  public static List<String> VALID_EXTENSIONS;
  @Value("${file.valid-mime-types}")
  public static List<String> VALID_MIME_TYPES;

  private static final String COMPRESS_FILENAME = "compressed.zip";

  @SuppressWarnings("static-access")
  @Value("${file.filePath}")
  private void setFilePath(String filePath) {
    this.FILE_PATH = filePath;
  }

  @SuppressWarnings("static-access")
  @Value("${file.fileTmpPath}")
  private void setFileTmpPath(String fileTmpPath) {
    this.FILE_TMP_PATH = fileTmpPath;
  }

  @SuppressWarnings("static-access")
  @Value("${file.valid-extensions}")
  private void setAllowType(List<String> validExtensions) {
    this.VALID_EXTENSIONS = validExtensions;
  }

  @SuppressWarnings("static-access")
  @Value("${file.valid-mime-types}")
  private void setAllowMINEType(List<String> validMimeTypes) {
    this.VALID_MIME_TYPES = validMimeTypes;
  }

  //uuid에서 '-'를 제거하여 리턴한다.
  public String getUUID() {
    return UUID.randomUUID().toString().replace("-", "");
  }

  public boolean isExists(String path) {
    File file = new File(path);
    return file.exists();
  }

  private boolean isValidExtension(String extension) {
    return VALID_EXTENSIONS.contains(extension);
  }

  private boolean isValidMimeType(String contentType) {
    return VALID_MIME_TYPES.stream().anyMatch(type -> type.startsWith(contentType));
  }

  public static String getExtension(String filename) {
    if (filename.contains(".")) {
      return filename.substring(filename.lastIndexOf(".") + 1);
    } else {
      return "";
    }
  }

  //파일 유효성 검증
  public boolean isValidFile(MultipartFile uploadfile) {
    String filename = Optional.ofNullable(uploadfile.getOriginalFilename()).orElse("");
    String extension = getExtension(filename);
    if (!isValidExtension(extension)) {
      return false;
    }

    Tika tika = new Tika();
    InputStream is = null;
    try {
      is = uploadfile.getInputStream();
      String mimeType = tika.detect(is);
      return isValidMimeType(mimeType);
    } catch (IOException e) {
      //FIXME 다국어 처리
      throw new BisiExcp("첨부파일 업로드중(유효성검증) 오류발생");
    } finally {
      try {
        if (is != null) {
          is.close();
        }
      } catch (IOException e) {
        //FIXME 다국어 처리
        throw new BisiExcp("첨부파일 업로드중(유효성검증) 오류발생");
      }
    }
  }

  //Delete file
  public void deleteFile(String path) {
    File file = new File(path);
    file.delete();
//		file.deleteOnExit();
  }

  //copy file
  public void copyFile(String srcPath, String destPath, boolean srcDeleteYn) throws IOException {
    File srcFile = new File(srcPath);
    File destFile = new File(destPath);
    //없으면 생성
    touch(destFile);
    //복제
    copyFile(srcFile, destFile);
    if (srcDeleteYn) {
      //tmp파일 삭제
      srcFile.delete();
    }
  }

  //Set Response Header
  public void setResponse(HttpServletRequest req, HttpServletResponse res, String filename,
      int fileLength) throws IOException {
    filename = getDownLoadFileNm(filename, req);
    res.setHeader("Content-Type", "application/x-download;charset=UTF-8");
    String disposition = "attachment";
    res.setHeader("Accept-Ranges", "bytes");
    if (req.getHeader("User-Agent").indexOf("MSIE 5.5") > -1) {
      res.setHeader("Content-Disposition", "filename=\"" + filename + "\";");

    } else if (req.getHeader("User-Agent").indexOf("Firefox") > -1) {
      res.setHeader("Content-Disposition", disposition + "; filename*=\"UTF-8''" + filename + "\"");

    } else {
      res.setHeader("Content-Disposition", disposition + "; filename=\"" + filename + "\";");

    }
    res.setHeader("Cache-control", "private");
    if (fileLength > 0) {
      res.setContentLength(fileLength);
    }
    res.setHeader("Content-Transfer-Encoding", "binary;");
    res.setHeader("Pragma", "no-cache;");
    res.setHeader("Expires", "-1;");
  }

  //make fileName(encode)
  public String getDownLoadFileNm(String filename, HttpServletRequest request) throws IOException {
    String browser = null;
    String header = request.getHeader("User-Agent");
    if (header.indexOf("MSIE") > -1) {
      browser = "MSIE";
    } else if (header.indexOf("Chrome") > -1) {
      browser = "Chrome";
    } else if (header.indexOf("Opera") > -1) {
      browser = "Opera";
    } else if (header.indexOf("Trident") > -1) { // IE11
      browser = "Trident";
    } else if (header.indexOf("Safari") > -1) {
      browser = "Safari";
    } else {
      browser = "Firefox";
    }

    String encodedFilename = null;

    if ("MSIE".equals(browser) || "Trident".equals(browser)) {
      try {
        encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
      } catch (UnsupportedEncodingException e) {
        log.error("IE 엑셀 파일명 변환중 에러 발생하였음");
      }

    } else if ("Chrome".equals(browser) || "Firefox".equals(browser)) {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < filename.length(); i++) {
        char c = filename.charAt(i);
        if (c > '~') {
          sb.append(URLEncoder.encode(String.valueOf(c), "UTF-8"));
        } else {
          sb.append(c);
        }
      }

      encodedFilename = sb.toString();

    } else if ("Safari".equals(browser)) {
      encodedFilename = filename; // 운영
    } else if ("Opera".equals(browser)) {
      encodedFilename = new String(filename.getBytes("UTF-8"), "8859_1");

    } else {
      throw new IOException("Not supported browser");
    }
    return encodedFilename;
  }

  //파일 다운로드
  public void sendFileStream(HttpServletRequest request, HttpServletResponse response,
      String fileNm, String filePath) {
    Path path = Paths.get(filePath);

    try {
      FileChannel inputChannel = FileChannel.open(path);
      WritableByteChannel outputChannel = Channels.newChannel(response.getOutputStream());
      int fileSize = (int) inputChannel.size();
      //response셋팅
      setResponse(request, response, fileNm, fileSize);
      inputChannel.transferTo(0, fileSize, outputChannel);
    } catch (IOException e) {
      //FIXME 다국어 처리
      throw new BisiExcp("파일스트림 처리중 문제가 발생하였습니다.");
    }
  }

  //파일 다운로드(zip)
  public void sendFileStream(HttpServletRequest request, HttpServletResponse response,
      String fileNm, List<ApndFileVo> list) {
    //zip foler 공간을 생성
    String zipFilePath = FILE_TMP_PATH + File.separator + getUUID();
    try {
      FileOutputStream fout = new FileOutputStream(getFile(zipFilePath));
      ZipOutputStream zout = new ZipOutputStream(fout, Charset.forName("UTF-8"));
      byte[] buffer = new byte[8096];
      int bytesRead;

      // 파일명 중복처리를 위한 MAP
      Map<String, Integer> fileNmMap = new HashMap<String, Integer>();

      for (ApndFileVo eachFile : list) {
        String srcPath = eachFile.getApndFilePath() + File.separator + eachFile.getApndFileId();
        File srcFile = new File(srcPath);

        try (FileInputStream fin = new FileInputStream(srcFile)) {
          String srcFileNm = eachFile.getApndFileNm();
          if (fileNmMap.containsKey(srcFileNm)) {
            int dupCnt = fileNmMap.get(srcFileNm);
            fileNmMap.put(srcFileNm, dupCnt + 1);
            srcFileNm = srcFileNm + "(" + String.valueOf(dupCnt) + ")";
          } else {
            fileNmMap.put(srcFileNm, 1);
          }

          zout.putNextEntry(new ZipEntry(srcFileNm + "." + eachFile.getApndFileExt()));
          while ((bytesRead = fin.read(buffer)) != -1) {
            zout.write(buffer, 0, bytesRead);
          }
        } catch (IOException e) {
          //FIXME 다국어처리
          throw new BisiExcp("파일 복사중 문제가 발생하였습니다.");
        }
      }
      zout.close();
      sendFileStream(request, response, fileNm + ".zip", zipFilePath);
    } catch (IOException e) {
      //FIXME 다국어처리
      throw new BisiExcp("파일 압축과정중 문제가 발생하였습니다.");
    }

  }

  public static File compressFiles(List<File> files) {
    return compressFiles(files, new File(COMPRESS_FILENAME));
  }

  public static File compressFiles(List<File> files, File destination) {
    try (ZipOutputStream zipOutputStream = new ZipOutputStream(
        Files.newOutputStream(FileUtils.getFile(destination).toPath()))) {
      for (File file : files) {
        ZipEntry zipEntry = new ZipEntry(file.getName());
        zipOutputStream.putNextEntry(zipEntry);
        IOUtils.copy(Files.newInputStream(file.toPath()), zipOutputStream);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return getFile(destination);
  }

  public static File compressFile(File source) {
    return compressFile(source, new File(COMPRESS_FILENAME));
  }

  public static File compressFile(File source, File destination) {
    try (ZipOutputStream zipOutputStream = new ZipOutputStream(
        Files.newOutputStream(destination.toPath()))) {
      ZipEntry zipEntry = new ZipEntry(source.getName());
      zipOutputStream.putNextEntry(zipEntry);
      IOUtils.copy(Files.newInputStream(source.toPath()), zipOutputStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return getFile(destination);
  }
}
