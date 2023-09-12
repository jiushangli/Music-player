package com.example.m5.logic.model


data class MusicSearchResponse(val result: Result, val code: String)


data class Result(val searchQcReminder: String, val songs: List<Song>, val songCount: Int)

data class Song(val name: String, val id: Long, val ar: List<Ar>, val al: Al, val free: Boolean)

data class Ar(val id: Long, val name: String)

data class Al(val id: Long, val name: String, val picUrl: String)

data class SongResponse(val data: List<Data>, val code: String)

data class Data(val id: Long, val url: String, val fee: Int, val time: Long)
