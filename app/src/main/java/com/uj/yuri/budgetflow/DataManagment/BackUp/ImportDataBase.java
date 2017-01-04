package com.uj.yuri.budgetflow.DataManagment.BackUp;

import android.os.Environment;

import com.uj.yuri.budgetflow.DataManagment.GatewayTemplate.GatewayTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by Yuri on 2016-01-17.
 */
public class ImportDataBase implements ImportExport {


    @Override
    public void moveDataBase() {
        try {
            File direct = new File(Environment.getExternalStorageDirectory() + "/BackupFolder");

            if(!direct.exists())
            {
                if(direct.mkdir())
                {
                    //directory is created;
                }

            }
            File sd = Environment.getExternalStorageDirectory();
            File data  = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String  currentDBPath= "//data//" + "com.uj.yuri.budgetflow"
                        + "//databases//" + GatewayTemplate.DATABASE_NAME;
                String backupDBPath  = "/BackupFolder/"+ GatewayTemplate.DATABASE_NAME;
                File  backupDB= new File(data, currentDBPath);
                File currentDB  = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

            }
        } catch (Exception e) {

        }
    }
}
