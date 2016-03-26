package controllers

import javax.inject._

import play.api.mvc._
import services.DGIIScraper
import play.api.libs.json._

/**
  * Created by jaime on 3/25/16.
  */
@Singleton
class ScrapingController @Inject()(dgii: DGIIScraper) extends Controller {

  def validateCustomer(documentId: String) = Action {
    Ok(Json.obj("isValid" -> JsBoolean(dgii.validateCustomer(documentId))))
  }

  def validateFiscalDocument(taxId: String, documentId: String) = Action {
    Ok(Json.obj("isValid" -> JsBoolean(dgii.validateFiscalDocument(taxId, documentId))))
  }

}
