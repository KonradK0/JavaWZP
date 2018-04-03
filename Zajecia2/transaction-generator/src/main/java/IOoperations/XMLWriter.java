package IOoperations;

import logic.TransactionGenerator;
import model.Item;
import model.Transaction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class XMLWriter implements OutputWriter {

    @Override
    public void saveToFile(long eventsCount, String outDir, TransactionGenerator transactionGenerator, List<String[]> namePriceList) {
        try {
            for (int i = 0; i < eventsCount; i++) {
                DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document doc = documentBuilder.newDocument();
                Element rootElem = doc.createElement("transaction");
                doc.appendChild(rootElem);
                Transaction transaction = transactionGenerator.generateTransaction(namePriceList, i);

                Element idElem = doc.createElement("id");
                idElem.setAttribute("id", String.valueOf(transaction.getId()));
                rootElem.appendChild(idElem);

                Element timeStampElem = doc.createElement("timeStamp");
                timeStampElem.setAttribute("timeStamp", transaction.getTimestamp());
                rootElem.appendChild(timeStampElem);

                Element customerIdElem = doc.createElement("customerId");
                customerIdElem.setAttribute("customerId", String.valueOf(transaction.getCustomerId()));
                rootElem.appendChild(customerIdElem);

                Element itemsElem = doc.createElement("items");
                for (Item item : transaction.getItems()) {
                    Element itemElem = doc.createElement("item");
                    Element itemNameElem = doc.createElement("name");
                    itemNameElem.setAttribute("name", item.getName());
                    itemElem.appendChild(itemNameElem);
                    Element itemQuantityElem = doc.createElement("quantity");
                    itemQuantityElem.setAttribute("quantity", String.valueOf(item.getQuantity()));
                    itemElem.appendChild(itemQuantityElem);
                    Element itemPriceElem = doc.createElement("price");
                    itemPriceElem.setAttribute("price", String.valueOf(item.getPrice()));
                    itemElem.appendChild(itemPriceElem);
                    itemsElem.appendChild(itemElem);
                }
                rootElem.appendChild(itemsElem);

                Element sumElem = doc.createElement("sum");
                sumElem.setAttribute("sum", String.valueOf(transaction.getSum()));
                rootElem.appendChild(sumElem);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(Paths.get(outDir, "order" + i + ".xml" ).toFile());
                transformer.transform(source,result);
            }
        } catch (ParserConfigurationException e) {
            logger.warn("ParserConfigurationException " + e.getMessage());
        } catch (javax.xml.transform.TransformerException e) {
            logger.warn("TransformerException " + e.getMessage());
        }
    }
}
