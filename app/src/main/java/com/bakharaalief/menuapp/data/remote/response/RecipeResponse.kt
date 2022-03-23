package com.bakharaalief.menuapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class Length(

	@field:SerializedName("number")
	val number: Int,

	@field:SerializedName("unit")
	val unit: String
)

data class StepsItem(

	@field:SerializedName("number")
	val number: Int,

	@field:SerializedName("ingredients")
	val ingredients: List<IngredientsItem>,

	@field:SerializedName("equipment")
	val equipment: List<EquipmentItem>,

	@field:SerializedName("step")
	val step: String,

	@field:SerializedName("length")
	val length: Length
)

data class EquipmentItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("localizedName")
	val localizedName: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)

data class IngredientsItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("localizedName")
	val localizedName: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)

data class RecipeResponseItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("steps")
	val steps: List<StepsItem>
)
