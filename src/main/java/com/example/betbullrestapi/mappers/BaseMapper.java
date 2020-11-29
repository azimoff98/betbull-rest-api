package com.example.betbullrestapi.mappers;

public interface BaseMapper<D, E> {
    D toDto(E e);
    E toEntity(D d);
}
