package uz.gita.bookapp.utils

import java.io.File


fun File.isFileExists(): Boolean {
    return this.exists() && !this.isDirectory
}