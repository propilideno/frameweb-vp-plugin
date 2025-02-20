package br.ufes.inf.nemo.frameweb.vp.model;

import java.util.logging.Level;
import br.ufes.inf.nemo.vpzy.logging.Logger;

/**
 * Enumeration of FrameWeb constraint types and their respective names, specifications, class types,
 * etc., which can be applied to association ends in the plug-in.
 *
 * @author Vítor E. Silva Souza (<a href="http://www.inf.ufes.br/~vitorsouza/">...</a>)
 * @author Igor Sunderhus e Silva (<a href="https://github.com/igorssilva">...</a>)
 */
public enum FrameWebAssociationEndConstraint {
  /* Constraints for association ends of Entity Model classes: */

  /* Association Collection */
  PERSISTENT_CLASS_COLLECTION_BAG("entity.persistent.collection.bag", "collection=bag",
      "collection=bag", false, FrameWebClass.PERSISTENT_CLASS, FrameWebClass.MAPPED_SUPERCLASS),

  PERSISTENT_CLASS_COLLECTION_LIST("entity.persistent.collection.list", "collection=list",
      "collection=list", false, FrameWebClass.PERSISTENT_CLASS, FrameWebClass.MAPPED_SUPERCLASS),

  PERSISTENT_CLASS_COLLECTION_MAP("entity.persistent.collection.map", "collection=map",
      "collection=map", false, FrameWebClass.PERSISTENT_CLASS, FrameWebClass.MAPPED_SUPERCLASS),

  PERSISTENT_CLASS_COLLECTION_SET("entity.persistent.collection.set", "collection=set",
      "collection=set", false, FrameWebClass.PERSISTENT_CLASS, FrameWebClass.MAPPED_SUPERCLASS),

  /* Association Cascade */
  PERSISTENT_CLASS_CASCADE_NONE("entity.persistent.cascade.none", "cascade=none",
      "cascade=none", false, FrameWebClass.PERSISTENT_CLASS, FrameWebClass.MAPPED_SUPERCLASS),

  PERSISTENT_CLASS_CASCADE_ALL("entity.persistent.cascade.all", "cascade=all",
      "cascade=all", false, FrameWebClass.PERSISTENT_CLASS, FrameWebClass.MAPPED_SUPERCLASS),

  PERSISTENT_CLASS_CASCADE_MERGE("entity.persistent.cascade.merge", "cascade=merge",
      "cascade=merge", false, FrameWebClass.PERSISTENT_CLASS, FrameWebClass.MAPPED_SUPERCLASS),

  PERSISTENT_CLASS_CASCADE_PERSIST("entity.persistent.cascade.persist", "cascade=persist",
  "cascade=persist", false, FrameWebClass.PERSISTENT_CLASS, FrameWebClass.MAPPED_SUPERCLASS),

  PERSISTENT_CLASS_CASCADE_REFRESH("entity.persistent.cascade.refresh", "cascade=refresh",
      "cascade=refresh", false, FrameWebClass.PERSISTENT_CLASS, FrameWebClass.MAPPED_SUPERCLASS),

  PERSISTENT_CLASS_CASCADE_REMOVE("entity.persistent.cascade.remove", "cascade=remove",
      "cascade=remove", false, FrameWebClass.PERSISTENT_CLASS, FrameWebClass.MAPPED_SUPERCLASS),

  /* Association Order By */
  PERSISTENT_CLASS_ORDER_NATURAL("entity.persistent.order.natural", "order=natural",
      "order=natural", false, FrameWebClass.PERSISTENT_CLASS, FrameWebClass.MAPPED_SUPERCLASS),

  PERSISTENT_CLASS_ORDER_ASCENDING("entity.persistent.order.ascending", "order=<columns> (ascending)",
      "order=<columns> (ascending)", true, FrameWebClass.PERSISTENT_CLASS, FrameWebClass.MAPPED_SUPERCLASS),

  PERSISTENT_CLASS_ORDER_DESCENDING("entity.persistent.order.descending", "order=<columns> (descending)",
      "order=<columns> (descending)", true, FrameWebClass.PERSISTENT_CLASS, FrameWebClass.MAPPED_SUPERCLASS),


  /* Fetch Type */
  PERSISTENT_CLASS_FETCH_EAGER("entity.persistent.fetch.eager", "fetch=eager",
      "fetch=eager", false, FrameWebClass.PERSISTENT_CLASS, FrameWebClass.MAPPED_SUPERCLASS),

  PERSISTENT_CLASS_FETCH_LAZY("entity.persistent.fetch.lazy", "fetch=lazy",
      "fetch=lazy", false, FrameWebClass.PERSISTENT_CLASS, FrameWebClass.MAPPED_SUPERCLASS),


  /* Not a FrameWeb association end constraint (default value). */
  NOT_A_FRAMEWEB_ASSOCIATION_END_CONSTRAINT("", "", "", false, FrameWebClass.NOT_A_FRAMEWEB_CLASS);

  /** The prefix used in the ID of context actions to add constraints. */
  private static final String PLUGIN_UI_CONTEXT_ACTION_PREFIX =
      "br.ufes.inf.nemo.frameweb.vp.actionset.context.association.end.menu.constraint.";

  /** The ID of the constraint in the plugin UI configuration. */
  private final String pluginUIID;

  /** The constraint's official name. */
  private final String name;

  /** The specification of the constraint. */
  private final String specification;

  /** Indicates if the constraint takes a value. */
  private final boolean parameterized;

  /** The classes to which the constraint can be applied. */
  private final FrameWebClass[] frameWebClasses;

  /** Array of constraints that are disjoint among themselves. */
  private FrameWebAssociationEndConstraint[] disjoints;

  FrameWebAssociationEndConstraint(String pluginUIID, String name, String specification,
      boolean parameterized, FrameWebClass... frameWebClasses) {
    this.pluginUIID = pluginUIID;
    this.name = name;
    this.specification = specification;
    this.parameterized = parameterized;
    this.frameWebClasses = frameWebClasses;
  }

  /**
   * Builds the disjoint arrays for the constraints that have disjoint sets. This method is called
   * lazily from {@code getDisjoints()} as setting it in the constructor doesn't work because of the
   * circular reference and having it in a {@code static} block was sometimes not working either and
   * I don't know why.
   */
  private static void initDisjoints() {
    Logger.log(Level.CONFIG,
        "Initializing disjoint arrays of FrameWeb association end constraints");

    // Entity Model > Persistent Class > collection={bag | list | map | set}.
    FrameWebAssociationEndConstraint[] collectionDisjoints =
        {PERSISTENT_CLASS_COLLECTION_BAG, PERSISTENT_CLASS_COLLECTION_LIST,
        PERSISTENT_CLASS_COLLECTION_MAP, PERSISTENT_CLASS_COLLECTION_SET};
    for (FrameWebAssociationEndConstraint constraint : collectionDisjoints) {
      constraint.disjoints = collectionDisjoints;
    }

    // Entity Model > Persistent Class > cascade={all | merge | persist | refresh | remove}.
    FrameWebAssociationEndConstraint[] cascadeDisjoints =
        {PERSISTENT_CLASS_CASCADE_ALL, PERSISTENT_CLASS_CASCADE_MERGE,
        PERSISTENT_CLASS_CASCADE_PERSIST, PERSISTENT_CLASS_CASCADE_REFRESH,
        PERSISTENT_CLASS_CASCADE_REMOVE};
    for (FrameWebAssociationEndConstraint constraint : cascadeDisjoints) {
      constraint.disjoints = cascadeDisjoints;
    }

    // Entity Model > Persistent Class > fetch={eager | lazy}.
    FrameWebAssociationEndConstraint[] fetchDisjoints =
        {PERSISTENT_CLASS_FETCH_EAGER, PERSISTENT_CLASS_FETCH_LAZY};
    for (FrameWebAssociationEndConstraint constraint : fetchDisjoints) {
      constraint.disjoints = fetchDisjoints;
    }

    // Entity Model > Persistent Class > order={natural | ascending | descending}.
    FrameWebAssociationEndConstraint[] orderDisjoints =
        {PERSISTENT_CLASS_ORDER_NATURAL, PERSISTENT_CLASS_ORDER_ASCENDING, PERSISTENT_CLASS_ORDER_DESCENDING};
    for (FrameWebAssociationEndConstraint constraint : orderDisjoints) {
      constraint.disjoints = orderDisjoints;
    }
  }

  public String getPluginUIID() {
    return pluginUIID;
  }

  public String getName() {
    return name;
  }

  public String getSpecification() {
    return specification;
  }

  public boolean isParameterized() {
    return parameterized;
  }

  public FrameWebClass[] getFrameWebClasses() {
    return frameWebClasses;
  }

  public FrameWebAssociationEndConstraint[] getDisjoints() {
    // Lazily initialize the disjoint sets.
    if (PERSISTENT_CLASS_COLLECTION_BAG.disjoints == null) {
      initDisjoints();
    }
    return disjoints;
  }

  /**
   * Provides the enum value that refers to a specific FrameWeb association end constraint given its
   * name.
   * 
   * @param name The name of the FrameWeb association end constraint.
   * @return An enum value that represents a FrameWeb association end constraint or
   *         {@code NOT_A_FRAMEWEB_ASSOCIATION_END_CONSTRAINT} if no association end constraint with
   *         the given name exists.
   */
  public static FrameWebAssociationEndConstraint of(String name) {
    FrameWebAssociationEndConstraint constraint = NOT_A_FRAMEWEB_ASSOCIATION_END_CONSTRAINT;
    for (FrameWebAssociationEndConstraint obj : FrameWebAssociationEndConstraint.values()) {
      if (obj.name.equalsIgnoreCase(name)) {
        constraint = obj;
      }
    }

    Logger.log(Level.FINE, "Providing FrameWeb association end constraint for name {0}: {1}",
        new Object[] {name, constraint});
    return constraint;
  }

  /**
   * Provides the enum value that refers to a specific FrameWeb association end constraint given its
   * plugin UI ID.
   * 
   * @param pluginUIID The ID of the FrameWeb association end constraint in the plugin UI
   *        configuration.
   * @return An enum value that represents a FrameWeb association end constraint or
   *         {@code NOT_A_FRAMEWEB_ASSOCIATION_END_CONSTRAINT} if no association end constraint with
   *         the given UI ID exists.
   */
  public static FrameWebAssociationEndConstraint ofPluginUIID(String pluginUIID) {
    FrameWebAssociationEndConstraint constraint = NOT_A_FRAMEWEB_ASSOCIATION_END_CONSTRAINT;
    for (FrameWebAssociationEndConstraint obj : FrameWebAssociationEndConstraint.values()) {
      String fullID = PLUGIN_UI_CONTEXT_ACTION_PREFIX + obj.pluginUIID;
      if (fullID.equalsIgnoreCase(pluginUIID)) {
        constraint = obj;
      }
    }

    Logger.log(Level.FINE,
        "Providing FrameWeb association end constraint for plug-in UI ID {0}: {1}",
        new Object[] {pluginUIID, constraint});
    return constraint;
  }

  /**
   * Provides the enum value that refers to a specific FrameWeb association end constraint given its
   * specification.
   * 
   * @param specification The specification used by the association end constraint.
   * @return An enum value that represents a FrameWeb association end constraint or
   *         {@code NOT_A_FRAMEWEB_ASSOCIATION_END_CONSTRAINT} if no association end constraint with
   *         the given specification exists.
   */
  public static FrameWebAssociationEndConstraint ofspecification(String specification) {
    FrameWebAssociationEndConstraint constraint = NOT_A_FRAMEWEB_ASSOCIATION_END_CONSTRAINT;
    for (FrameWebAssociationEndConstraint obj : FrameWebAssociationEndConstraint.values()) {
      if (obj.specification.equalsIgnoreCase(specification)) {
        constraint = obj;
      }
    }

    Logger.log(Level.FINE,
        "Providing FrameWeb association end constraint for specification {0}: {1}",
        new Object[] {specification, constraint});
    return constraint;
  }
}
