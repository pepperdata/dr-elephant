package com.linkedin.drelephant

class RealmContext extends play.mvc.PathBindable[RealmContext] {
  def name: String = null
  def context: ElephantContext = null

  def bind(key: String, name: String): RealmContext = {
    val context = ElephantContext.instance(name)
    if (context == null)
      throw new IllegalArgumentException(s"realm [${name}] not found")
    new NamedRealmContext(name, context)
  }

  def javascriptUnbind(): String = null

  def unbind(key: String): String = {
    throw new UnsupportedOperationException("Cannot unbind generic context")
  }
}

class NamedRealmContext(override val name: String, override val context: ElephantContext) extends RealmContext {
  override def unbind(key: String): String = {
    name
  }
}
