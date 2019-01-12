package module3;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 法律查询获得的id，名称，简介，内容,章的名称，每章所含法律法规的数目；
 * Created by 白海涛 on 2017/5/26.
 */


public class Law implements Parcelable{
    private int id;
    private String name;
    private String digest;
    private String catalogString;
    private String text;

//    private ArrayList<String> chapterName;
//    private ArrayList<Integer> numberInChapters;
    private int tollItems;

    public int getTollItems() {
        return tollItems;
    }

    public void setTollItems(int tollItems) {
        this.tollItems = tollItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCatalogString() {
        return catalogString;
    }

    public void setCatalogString(String catalogString) {
        this.catalogString = catalogString;
    }
//    public ArrayList<String> getChapterName() {
//        return chapterName;
//    }
//
//    public void setChapterName(ArrayList<String> chapterName) {
//        this.chapterName = chapterName;
//    }
//
//    public ArrayList<Integer> getNumberInChapters() {
//        return numberInChapters;
//    }
//
//    public void setNumberInChapters(ArrayList<Integer> numberInChapters) {
//        this.numberInChapters = numberInChapters;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(digest);
        dest.writeString(catalogString);
        dest.writeString(text);
//        dest.writeStringList(chapterName);
//        dest.writeList(numberInChapters);
        dest.writeInt(tollItems);
    }


    public static final Creator<Law> CREATOR = new Creator<Law>() {
        @Override
        public Law createFromParcel(Parcel source) {
            Law law = new Law();
            law.id = source.readInt();
            law.name = source.readString();
            law.digest = source.readString();
            law.catalogString = source.readString();
            law.text = source.readString();
//            law.chapterName = source.readStringList();
//            law.numberInChapters = source.readList();
            law.tollItems = source.readInt();
            return law;
        }

        @Override
        public Law[] newArray(int size) {
            return new Law[size];
        }
    };
}
