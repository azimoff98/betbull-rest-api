package com.example.betbullrestapi.mapper;

public interface BaseMapper<D, E> {
    D toDto(E e);
    E toEntity(D d);
}
