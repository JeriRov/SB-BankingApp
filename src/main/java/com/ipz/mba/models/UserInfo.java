package com.ipz.mba.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public record UserInfo(String firstName, String lastName) { }
