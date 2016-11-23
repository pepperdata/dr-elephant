package com.linkedin.drelephant

import com.avaje.ebean.EbeanServer
import com.avaje.ebean.EbeanServerFactory
import com.avaje.ebean.config.DataSourceConfig
import com.avaje.ebean.config.NamingConvention
import com.avaje.ebean.config.ServerConfig
import com.avaje.ebean.config.TableName
import com.avaje.ebean.config.UnderscoreNamingConvention

class RealmEbeanContext(name: String, appConfig: play.Configuration) {
  val prefix = name.replaceAll("-", "_")
  val db = new DataSourceConfig()
  db.setDriver(appConfig.getString("db.default.driver"))
  db.setUsername(appConfig.getString("db.default.user", ""))
  db.setPassword(appConfig.getString("db.default.password", ""))
  db.setUrl(appConfig.getString("db.default.url"))
  val naming = new UnderscoreNamingConvention() {
    @Override
    override def getTableName(beanClass: Class[_]): TableName = {
      val t = super.getTableName(beanClass)
      new TableName(t.getCatalog, t.getSchema, s"${prefix}__${t.getName}")
    }
  };
  val config = new ServerConfig()
  config.setName(name)
  config.loadFromProperties()
  config.setDdlGenerate(true)
  config.setDdlRun(true)
  config.setDefaultServer(false)
  config.setRegister(true)
  config.setDataSourceConfig(db)
  config.setNamingConvention(naming)
  //config.addPackage("models")
  config.addClass(classOf[models.AppResult])
  config.addClass(classOf[models.AppHeuristicResult])
  config.addClass(classOf[models.AppHeuristicResultDetails])
  val server = EbeanServerFactory.create(config)
}
