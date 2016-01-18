package com.uj.yuri.budgetflow.db_managment.BackUp;

import android.os.Environment;

import com.uj.yuri.budgetflow.db_managment.DateBaseHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by Yuri on 2016-01-17.
 */
public class ExportDataBase implements ImportExport {

    @Override
    public void moveDataBase() {
        try
        {
            File direct = new File(Environment.getExternalStorageDirectory() + "/BackupFolder");

            if (!direct.exists())
            {
                if (direct.mkdir())
                {
                    //directory is created;
                }

            }
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite())
            {
                String  currentDBPath= "//data//" + "com.uj.yuri.budgetflow"
                        + "//databases//" + DateBaseHelper.DATABASE_NAME;
                String backupDBPath  = "/BackupFolder/"+ DateBaseHelper.DATABASE_NAME;
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

            }
        } catch (Exception e){}
    }
}
