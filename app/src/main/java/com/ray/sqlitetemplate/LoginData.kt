package com.ray.sqlitetemplate

 class LoginData(/*private var uniqueId:Long, */private var id:String, private var pw:String){
   /* var mUniqueId = uniqueId
     get()=field
     set(value){
         field=value
     }
*/
    var mLoginID:String = id
        get() = field
        set(value) {
            field = value
        }
    var mLoginPW:String =pw
        get() = field
        set(value) {
            field = value
        }
}