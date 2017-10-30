package Tools;
import java.io.*;
/**
 * Created by shiqun on 2016/4/25.
 */
//读取文件
public class fileTool
{
    public String getContent(String fileUrl)
    {
        //
        StringBuffer buff = new StringBuffer();
        String temp;

        try
        {
            //
            BufferedReader br = new BufferedReader(new
                    InputStreamReader(new FileInputStream(fileUrl)));

            //
            while((temp = br.readLine())!=null)
            {
                buff.append(temp+"\n");
            }

            //关闭读写;
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return buff.toString();
    }
}
