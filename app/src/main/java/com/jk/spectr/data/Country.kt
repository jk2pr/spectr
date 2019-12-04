package com.jk.spectr.data

data class Country(

    val _id: String,
    val company: String,
    val website: String,
    val logo: String,
    val about: String,
    val members: List<Members>

) {
    class Members(
        val _id: String,
        val age: Int,
        val email: String,
        val phone: String,
        val name: Names


    ) {
        class Names(

            val first: String,
            val second: String
        )
    }


}
