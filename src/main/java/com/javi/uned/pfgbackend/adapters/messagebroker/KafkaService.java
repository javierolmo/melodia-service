package com.javi.uned.pfgbackend.adapters.messagebroker;

public class KafkaService {


    /* TODO: Cambiar a REST
    public void consumePDF(byte[] rawFile, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) throws IOException {

        long keyLong = Long.parseLong(key);

        // Receive and save pdf
        File dir = fileService.getSheetFolder(Long.parseLong(key));
        File pdfFile = new File(String.format("%s/%s%s", dir.getAbsolutePath(), key, Formats.PDF));
        FileUtils.writeByteArrayToFile(pdfFile, rawFile);

        // Mark as finished
        if (fileService.hasPDF(keyLong) && fileService.hasXML(keyLong)) {
            int id = Integer.parseInt(key);
            sheetService.markAsFinished(id);
        }
    }

    @KafkaListener(topics = CONSUME_COMPOSER_GENETIC_XML, groupId = "0", containerFactory = "fileListenerFactory")
    public void consumeXML(byte[] rawFile, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) throws IOException {

        long keyLong = Long.parseLong(key);

        // Receive and save xml
        File dir = fileService.getSheetFolder(keyLong);
        File musicxmlFile = new File(String.format("%s/%s%s", dir.getAbsolutePath(), key, Formats.MUSICXML));
        FileUtils.writeByteArrayToFile(musicxmlFile, rawFile);

        // Mark as finished
        if (fileService.hasPDF(keyLong) && fileService.hasXML(keyLong)) {
            int id = Integer.parseInt(key);
            sheetService.markAsFinished(id);
        }
    }
     */



}
