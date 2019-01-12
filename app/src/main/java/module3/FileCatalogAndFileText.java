package module3;

import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;


/**
 * 获得法律的章数，以及法律每章下的条数
 * 获得法律每条下的具体内容
 * Created by 白海涛 on 2017/5/13.
 */

public class FileCatalogAndFileText {
    private static final String SEP = "#";

    public static String getFileCatalogString(File filePath) {
        StringBuffer stringBuffer = new StringBuffer("");
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s = bufferedReader.readLine();
            stringBuffer.append(s);
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    public static String getFileCatalogString(String filePaht) {
        String fileCatologString = "";
        try {
            File file = new File(filePaht);
            fileCatologString = getFileCatalogString(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileCatologString;

    }

    public static List<Map<String, Object>> getFileCatalog(File filePath) {
        StringBuffer stringBuffer = new StringBuffer("");
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s = bufferedReader.readLine();
            stringBuffer.append(s);
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String catalog = stringBuffer.toString();
        return getCatalog(catalog);
    }

    @NonNull
    public static List<Map<String, Object>> getCatalog(String catalog) {
        String[] strings = catalog.split(SEP);
        int length = strings.length;
        List<Map<String, Object>> catalogList = new ArrayList<>();
        for (int i = 0; i <length ; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("Chapter", strings[i]);
            map.put("NumberInChapter", Integer.parseInt(strings[i + 1]));
            catalogList.add(map);
            i++;
        }
        return catalogList;
    }

    public static List<Map<String, Object>> getFileCatalog(String filePath) {
        List<Map<String, Object>> catalogList1 = new ArrayList<>();
        try
        {
            File f = new File(filePath);
            catalogList1 = getFileCatalog(f);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return catalogList1;
    }

    //读取一个文件的所有内容
    public static String getFileText(File f)
    {
        StringBuffer sb=new StringBuffer("");
        try
        {
            FileReader fr=new FileReader(f);
            BufferedReader br=new BufferedReader(fr);

            String s1 = br.readLine();
            String s=br.readLine();

            while(s!=null)
            {
                sb.append(s);
                s=br.readLine();
            }
            br.close();
        }
        catch(Exception e)
        {
            sb.append("");
        }

        return sb.toString();
    }

    //读取一个文件的所有内容--重载
    public static String getFileText(String s)
    {
        String t = "";
        try
        {
            File f = new File(s);
            t = getFileText(f);
        }
        catch(Exception e)
        {
            t = "";
        }

        return t;
    }

    public static void main(String[] args) throws IOException{
        List<Map<String, Object>> catolog = FileCatalogAndFileText.getFileCatalog("法律法规查询/辐射监管相关法律/《中华人民共和国放射性污染防治法》.txt");
        String text = FileCatalogAndFileText.getFileText("法律法规查询/辐射监管相关法律/《中华人民共和国放射性污染防治法》.txt");
//        List<Map<String, Object>> catolog = FileCatalogAndFileText.getFileCatalog(args[0]);
//        String text = FileCatalogAndFileText.getFileText(args[0]);
        int size = catolog.size();
        System.out.println(size);
        System.out.println(text);

        ListIterator<Map<String, Object>> iterator = catolog.listIterator();
        Map<String, Object> map;
        ArrayList<String> chapterName = new ArrayList<>();
        ArrayList<Integer> numberInChapters = new ArrayList<>();
        int pos = 0;
        int tollItems = 0;
        while (iterator.hasNext()) {
            map = iterator.next();
            chapterName.add(pos, (String) map.get("Chapter"));
            numberInChapters.add(pos, (int) map.get("NumberInChapter"));
            tollItems = tollItems + numberInChapters.get(pos);
            pos++;
        }
        System.out.println(tollItems);
        System.out.println(chapterName);
        System.out.println(numberInChapters);

        //获得法律法规的条款内容
        int tollcut = 0;
        for (int j = 0; j < chapterName.size(); j++) {
            String chap = chapterName.get(j);
            int cut = numberInChapters.get(j);
            for (int k = tollcut; k < cut + tollcut; k++) {
                int from = text.indexOf("第" + (k + 1) + "条");
                int to = text.indexOf("第" + (k + 2) + "条");
                if (k != tollItems - 1) {
                    int length = ("第" + (k + 1) + "条　").length();
                    String itemTile = "《中华人民共和国放射性污染防治法》" + "  " + chap + "  第" + (k + 1) + "条";
                    String itemText = text.substring(from + length, to);
//                    field = new Field("itemTile", itemTile, Field.Store.YES, Field.Index.UN_TOKENIZED);
//                    doc.add(field);
//                    field = new Field(itemTile, itemText, Field.Store.YES, Field.Index.TOKENIZED);
//                    doc.add(field);
                    System.out.println(itemTile);
                    System.out.println(itemText);
                } else {
                    int length = ("第" + (k + 1) + "条　").length();
                    String itemTile = "《中华人民共和国放射性污染防治法》" + "  " + chap + "  第" + (k + 1) + "条";
                    String itemText = text.substring(from + length);
//                    field = new Field("itemTile", itemTile, Field.Store.YES, Field.Index.UN_TOKENIZED);
//                    doc.add(field);
//                    field = new Field(itemTile, itemText, Field.Store.YES, Field.Index.TOKENIZED);
//                    doc.add(field);
                    System.out.println(itemTile);
                    System.out.println(itemText);
                }
            }
            tollcut = tollcut + cut;
        }
        System.out.println("finished");
    }

}
