package com.xxx.collect.core.util.zip.zip4j;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class DemoLuju {

  public static void main(String[] args) throws ZipException, IOException {
    File tempFile = new File("F:\\imageTest\\zip\\zip4j-1.zip");
    if (tempFile.exists())
      tempFile.delete();
    ZipFile zipFile = new ZipFile(tempFile);
    ZipParameters parameters = new ZipParameters();
    parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
    parameters.setSourceExternalStream(true);
    parameters.setFileNameInZip("ppp/sifon_SYFON DI_A GEÇEN GÖVDE APARATI_1.SLDPRT");

    InputStream is = new ByteArrayInputStream("456".getBytes());

    zipFile.addStream(is, parameters);
    
    File tempFile2 = File.createTempFile("123", "");
    File file = new File(tempFile2.getParentFile(),"sifon_SYFON DI_A GEÇEN GÖVDE APARATI_1.SLDPRT");
    file.mkdir();
    ZipParameters parameters2 = new ZipParameters();
    parameters2.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
    zipFile.addFolder(file, parameters2);
  }

}
