package com.yanbober.support_library_demo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author dennis
 *
 * @param <T>
 *            代表需要写入或者读出返回的类型
 */
public  class SerializeUtil<T>{

    /**
     *
     * @param data
     *            需要写入的数据
     * @param file
     *            对应的文件名
     */
    public void writeDataToFile(T data, String file){
        ObjectOutputStream os = null;
        try{
            File fileff = new File(file);
            if (!fileff.exists()){
                fileff.createNewFile();
            }
            os = new ObjectOutputStream(new FileOutputStream(fileff));
            os.writeObject(data);// 将List列表写进文件
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            if (os != null){
                try{
                    os.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *
     * @param flie
     *            对应的文件名
     * @return	读取文件中的数据;
     */
    public T readDataToFile(String flie){
        ObjectInputStream is = null;
        File f=new File(flie);
        T data = null;
        if(!f.exists()) return null;

        try{
            is = new ObjectInputStream(new FileInputStream(flie));
            data = (T) is.readObject();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }finally{
            if (is != null){
                try{
                    is.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return data;
    }
}
