package com.lsitc.global.auditing;

import java.time.temporal.TemporalAccessor;

public interface SoftDeletable<U, T extends TemporalAccessor> {

  boolean isDeleted();

  U getDeletedBy();

  void setDeletedBy(U deletedBy);

  T getDeletedDate();

  void setDeletedDate(T deletedDate);

  void delete();
}
