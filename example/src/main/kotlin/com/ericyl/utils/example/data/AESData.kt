package com.ericyl.utils.example.data

import com.ericyl.utils.example.annotaions.PoKo

/**
 * Created by ericyl on 2017/8/7.
 */

const val TABLE_NAME = "aes_info"
const val INDEX = "id"
const val NAME = "name"
const val TYPE = "type"
const val PWD = "pwd"
const val ENTRY_ALIAS = "entry_alias"
const val ENTRY_PWD = "entry_pwd"

@PoKo
data class AESData(val name: String, val type: String, val pwd: String, val entryAlias: String, val entryPwd: String)