package com.github.xuyh.java.learning.base.reflection;

import java.util.Date;

public class Taco {
  private Long id;
  @NameValidator(min = 3, max = 10)
  private String name;
  private Date createTime;


  public Taco() {
  }

  public Taco(Long id, String name, Date createTime) {
    this.id = id;
    this.name = name;
    this.createTime = createTime;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
}
