package uz.gita.bookapp_slp.utils

import java.io.File


fun File.isFileExists(): Boolean {
    return this.exists() && !this.isDirectory
}