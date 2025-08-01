// Code generated by moshi-kotlin-codegen. Do not edit.
@file:Suppress("DEPRECATION", "unused", "UNUSED_PARAMETER", "ClassName", "REDUNDANT_PROJECTION",
    "RedundantExplicitType", "LocalVariableName", "RedundantVisibilityModifier",
    "PLATFORM_CLASS_MAPPED_TO_KOTLIN", "IMPLICIT_NOTHING_TYPE_ARGUMENT_IN_RETURN_POSITION")

package com.example.mcnews.`data`.remote.dto

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.`internal`.Util
import java.lang.NullPointerException
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.emptySet
import kotlin.text.buildString

public class TagDtoJsonAdapter(
  moshi: Moshi,
) : JsonAdapter<TagDto>() {
  private val options: JsonReader.Options = JsonReader.Options.of("TagId", "Name")

  private val intAdapter: JsonAdapter<Int> = moshi.adapter(Int::class.java, emptySet(), "tagId")

  private val stringAdapter: JsonAdapter<String> = moshi.adapter(String::class.java, emptySet(),
      "name")

  public override fun toString(): String = buildString(28) {
      append("GeneratedJsonAdapter(").append("TagDto").append(')') }

  public override fun fromJson(reader: JsonReader): TagDto {
    var tagId: Int? = null
    var name: String? = null
    reader.beginObject()
    while (reader.hasNext()) {
      when (reader.selectName(options)) {
        0 -> tagId = intAdapter.fromJson(reader) ?: throw Util.unexpectedNull("tagId", "TagId",
            reader)
        1 -> name = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull("name", "Name",
            reader)
        -1 -> {
          // Unknown name, skip it.
          reader.skipName()
          reader.skipValue()
        }
      }
    }
    reader.endObject()
    return TagDto(
        tagId = tagId ?: throw Util.missingProperty("tagId", "TagId", reader),
        name = name ?: throw Util.missingProperty("name", "Name", reader)
    )
  }

  public override fun toJson(writer: JsonWriter, value_: TagDto?): Unit {
    if (value_ == null) {
      throw NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.")
    }
    writer.beginObject()
    writer.name("TagId")
    intAdapter.toJson(writer, value_.tagId)
    writer.name("Name")
    stringAdapter.toJson(writer, value_.name)
    writer.endObject()
  }
}
