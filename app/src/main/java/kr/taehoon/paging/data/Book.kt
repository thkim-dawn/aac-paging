package kr.taehoon.paging.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

open class Book(
    @SerializedName("authors")
    @Expose
    val authors: List<String>,
    @SerializedName("contents")
    @Expose
    val contents: String,
    @SerializedName("datetime")
    @Expose
    val datetime: Date,
    @SerializedName("isbn")
    @Expose
    val isbn: String,
    @SerializedName("price")
    @Expose
    val price: Int,
    @SerializedName("publisher")
    @Expose
    val publisher: String,
    @SerializedName("sale_price")
    @Expose
    val salePrice: Int,
    @SerializedName("status")
    @Expose
    val status: String,
    @SerializedName("thumbnail")
    @Expose
    val thumbnail: String,
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("translators")
    @Expose
    val translators: List<String>,
    @SerializedName("url")
    @Expose
    val url: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createStringArrayList() ?: ArrayList(),
        parcel.readString() ?: "",
        Date(parcel.readLong()),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: ArrayList(),
        parcel.readString() ?: ""
    ) {
    }

    fun getAuthorsText(): String {
        return authors.joinToString()
    }

    fun getTranslatorsText(): String {
        return translators.joinToString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(authors)
        parcel.writeString(contents)
        parcel.writeLong(datetime.time)
        parcel.writeString(isbn)
        parcel.writeInt(price)
        parcel.writeString(publisher)
        parcel.writeInt(salePrice)
        parcel.writeString(status)
        parcel.writeString(thumbnail)
        parcel.writeString(title)
        parcel.writeStringList(translators)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}

   