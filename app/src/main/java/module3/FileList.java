package module3;

/**
 * Created by 白海涛 on 2017/4/11.
 */

import java.io.File;
import java.io.IOException;

public class FileList
{
    private static final String SEP = "#";
    private static StringBuffer sb = new StringBuffer("");

    //返回数组
    public static String[] getFiles(File f) throws IOException
    {
        //file.isDirectory():判断某个文件是不是文件夹，是返回true，不是返回false；
        if(f.isDirectory())
        {
            File[] fs=f.listFiles();
            for(int i=0;i<fs.length;i++)
            {
                getFiles(fs[i]);
            }
        }
        else
        {
            sb.append(f.getPath() + SEP);
        }
        String s = sb.toString();
        String[] list = s.split(SEP);
        return list;
    }

    //返回数组--重载
    public static String[] getFiles(String t) throws IOException
    {
        File f = new File(t);

        f.mkdirs();
        if(f.isDirectory())
        {
            File[] fs=f.listFiles();
            //file.listFiles()方法是返回某个目录下所有文件和目录的绝对路径，返回的是File数组
            //file.list()方法是返回某个目录下的所有文件和目录的文件名，返回的是String数组
            for(int i=0;i<fs.length;i++)
            {
                getFiles(fs[i]);
            }
        }
        else
        {
            //file.getPath()返回的是定义时的路径，可能是相对路径，也可能是绝对路径，这个取决于定义时用的是相对路径还是绝对路径
            sb.append(f.getPath() + SEP);
        }
        String s = sb.toString();
        String[] list = s.split(SEP);
        return list;
    }

    //主函数，测试
    public static void main(String[] args) throws IOException
    {
        //String s[] = FileList.getFiles("法律法规查询\\辐射监管相关法律");
         String s[] = FileList.getFiles(args[0]);

        for(int i=0;i<s.length;i++)
        {
            File f = new File(s[i]);
            System.out.println(s[i]);
//            String name = MainActivity.getLawsName(f);
//            System.out.println(name);
        }
        System.out.println("结束");

    }
}