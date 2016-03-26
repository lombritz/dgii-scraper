package controllers

import javax.inject._

import play.api.mvc._

/**
  * Created by jaime on 3/25/16.
  */
@Singleton
class ApplicationController @Inject() extends Controller {

  def index = Action {
    Ok(views.html.index("DGII Rest API (Scraper)"))
  }

}
