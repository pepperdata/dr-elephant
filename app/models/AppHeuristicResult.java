/*
 * Copyright 2016 LinkedIn Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linkedin.drelephant.analysis.Severity;
import com.linkedin.drelephant.util.Utils;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Model;

import com.avaje.ebean.annotation.Indices;
import com.avaje.ebean.annotation.Index;

@Entity
@Table(name = "yarn_app_heuristic_result")
@Indices(value = {@Index(columnNames={"heuristic_name","severity"})})
public class AppHeuristicResult extends Model {

  private static final long serialVersionUID = 2L;

  public static final int HEURISTIC_NAME_LIMIT = 128;
  public static final int HEURISTIC_CLASS_LIMIT = 255;

  public static class TABLE {
    public static final String TABLE_NAME = "yarn_app_heuristic_result";
    public static final String ID = "id";
    public static final String APP_RESULT_ID = "yarnAppResult";
    public static final String HEURISTIC_NAME = "heuristicName";
    public static final String SEVERITY = "severity";
    public static final String SCORE = "score";
    public static final String APP_HEURISTIC_RESULT_DETAILS = "yarnAppHeuristicResultDetails";
  }

  public static String getSearchFields() {
    return Utils.commaSeparated(AppHeuristicResult.TABLE.HEURISTIC_NAME, AppHeuristicResult.TABLE.SEVERITY);
  }

  @JsonIgnore
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  public int id;

  @JsonBackReference
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(nullable = false)
  public AppResult yarnAppResult;

  @Column(length = HEURISTIC_CLASS_LIMIT, nullable = false)
  public String heuristicClass;

  @Column(length = HEURISTIC_NAME_LIMIT, nullable = false)
  public String heuristicName;

  @Column(nullable = false, columnDefinition = "TINYINT UNSIGNED")
  public Severity severity;

  @Column(nullable = true, columnDefinition = "MEDIUMINT UNSIGNED DEFAULT 0")
  public int score;

  @JsonManagedReference
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "yarnAppHeuristicResult")
  public List<AppHeuristicResultDetails> yarnAppHeuristicResultDetails;

  @Override
  public boolean delete() {
    throw new IllegalArgumentException("must use delete(String server)");
  }

  @Override
  public boolean deletePermanent() {
    throw new IllegalArgumentException("deletePermanent not supported");
  }

  @Override
  public void insert() {
    throw new IllegalArgumentException("must use insert(String server)");
  }

  @Override
  public void markAsDirty() {
    throw new IllegalArgumentException("markAsDirty not supported");
  }

  @Override
  public void refresh() {
    throw new IllegalArgumentException("refresh not supported");
  }

  @Override
  public void save() {
    throw new IllegalArgumentException("must use save(String server)");
  }

  public void save(String server) {
    db(server).save(this);
  }

  @Override
  public void update() {
    throw new IllegalArgumentException("must use update(String server)");
  }
}
