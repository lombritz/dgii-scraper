package services

import javax.inject.Singleton


/**
  * Created by jaime on 3/25/16.
  */
trait DGIIScraper {

  /**
    * Validates a customer given it's document id.
    *
    * @param documentId the customer RNC or ID No.
    * @return true when client is active on the DGII, otherwise false.
    */
  def validateCustomer(documentId: String): Boolean

  /**
    * Validates a fiscal document.
    *
    * @param taxId the company tax id (RNC).
    * @param documentNo the document number issued by the company (NCF).
    * @return true when document has a valid fiscal no. on the issuing company, otherwise false.
    */
  def validateFiscalDocument(taxId: String, documentNo: String): Boolean

}

import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HtmlPage

import scala.collection.JavaConversions._

/**
  * Created by jaime on 3/25/16.
  */
@Singleton
class HtmlUnitDGIIScraper extends DGIIScraper {

  private val webClient = new WebClient

  /**
    * Validates a customer given it's document id.
    *
    * @param documentId the customer RNC or ID No.
    * @return true when client is active on the DGII, otherwise false.
    */
  override def validateCustomer(documentId: String): Boolean = {
    val page: HtmlPage = webClient.getPage("http://www.dgii.gov.do/app/WebApps/Consultas/rnc/RncWeb.aspx")

    page.getElementById("txtRncCed").setAttribute("value", documentId)

    val btn = page.getElementById("btnBuscaRncCed")

    val result: HtmlPage = btn.click()

    val dgResultados = Option(result.getElementById("dgResultados"))

    dgResultados.nonEmpty
  }

  /**
    * Validates a fiscal document.
    *
    * @param taxId      the company tax id (RNC).
    * @param documentNo the document number issued by the company (NCF).
    * @return true when document has a valid fiscal no. on the issuing company, otherwise false.
    */
  override def validateFiscalDocument(taxId: String, documentNo: String): Boolean = {
    val page: HtmlPage = webClient.getPage("http://www.dgii.gov.do/app/WebApps/Consultas/NCF/ConsultaNCF.aspx")

    page.getElementById("txtRNC").setAttribute("value", taxId)
    page.getElementById("txtNCF").setAttribute("value", documentNo)

    val btn = page.getElementById("btnConsultar")

    val result: HtmlPage = btn.click()

    val spanContribuyente = Option(result.getElementById("lblContribuyente"))

    val spanTipoComprobante = Option(result.getElementById("lblTipoComprobante"))

    spanContribuyente.nonEmpty && spanTipoComprobante.nonEmpty
  }
}