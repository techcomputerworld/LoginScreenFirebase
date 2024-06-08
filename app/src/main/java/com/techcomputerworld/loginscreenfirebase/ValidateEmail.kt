package com.techcomputerworld.helpchatgpt

import java.util.regex.Matcher
import java.util.regex.Pattern

class ValidateEmail {
    companion object {
        var pat: Pattern?= null
        var mat: Matcher?= null

        fun isEmail(email:String):Boolean {
            //antes de la @ tenemos todos los caracteres validos
            //despues de la @ tenemos letras y numeros de A a Z mayusculas y minusculas y de 0 a 9 y de 2 a 4 digitos 
            //pat = Pattern.compile("^[\\w\\-\\_\\+]+(\\.[\\w\\-\\_]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$")
            pat = Pattern.compile("^[\\w\\-\\_\\+]+(\\.[\\w\\-\\_]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,24}$")

            mat = pat!!.matcher(email)
            return mat!!.find()
        }
    }
}