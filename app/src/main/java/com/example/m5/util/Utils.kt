package com.example.m5.util

import java.io.File


fun deleteMusic(filePath: String): Boolean {
    val file = File(filePath)
/*    val dos = DataOutputStream(FileOutputStream(file))
    dos.close()*/
    if (file.exists()) {
        // 删除文件
        if (file.delete()) {
            return true // 删除成功
        }
    }
    return false // 删除失败
}

