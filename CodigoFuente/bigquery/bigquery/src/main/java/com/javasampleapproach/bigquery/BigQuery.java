package com.javasampleapproach.bigquery;


import java.io.IOException;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.BigqueryScopes;
import com.google.api.services.bigquery.model.GetQueryResultsResponse;
import com.google.api.services.bigquery.model.QueryRequest;
import com.google.api.services.bigquery.model.QueryResponse;
import com.google.api.services.bigquery.model.TableCell;
import com.google.api.services.bigquery.model.TableRow;

/**
 * Example of authorizing with BigQuery and reading from a public wikipedia dataset.
 *
 */
public class BigQuery {
  
  /**
   * using Application Default Credentials for Authentication client service .
   * 
   */
  public static Bigquery createAuthorizedClient() throws IOException {
    // Create the credential
    HttpTransport transport = new NetHttpTransport();
    JsonFactory jsonFactory = new JacksonFactory();
    GoogleCredential credential = GoogleCredential.getApplicationDefault(transport, jsonFactory);

    if (credential.createScopedRequired()) {
      credential = credential.createScoped(BigqueryScopes.all());
    }

    return new Bigquery.Builder(transport, jsonFactory, credential)
        .setApplicationName("Bigquery Samples")
        .build();
  }
  
  /**
   * Executes the given query synchronously.
   */
  private static List<TableRow> executeQuery(String querySql, Bigquery bigquery, String projectId)
      throws IOException {
    QueryResponse query =
        bigquery.jobs().query(projectId, new QueryRequest().setQuery(querySql)).execute();

    // Execute it
    GetQueryResultsResponse queryResult =
        bigquery
            .jobs()
            .getQueryResults(
                query.getJobReference().getProjectId(), query.getJobReference().getJobId())
            .execute();

    return queryResult.getRows();
  }

  /**
   * Display result to console
   */
  private static void displayResults(List<TableRow> rows) {
    System.out.print("\nResults:\n------------\n");
    for (TableRow row : rows) {
      for (TableCell field : row.getF()) {
        System.out.printf("%-50s", field.getV());
      }
      System.out.println();
    }
  }

  /**
   * Exercises the methods defined in this class.
   *
   * In particular, it creates an authorized Bigquery service object using Application Default
   * Credentials, then executes a query against the public Shakespeare dataset and prints out the
   * results.
   *
   * @param args the first argument, if it exists, should be the id of the project to run the test
   *     under. If no arguments are given, it will prompt for it.
   * @throws IOException if there's an error communicating with the API.
   */
  public static void main(String[] args) throws IOException {
    
    String projectId = "neon-deployment-141106";

    // Create a new Bigquery client authorized via Application Default Credentials.
    Bigquery bigquery = createAuthorizedClient();

    List<TableRow> rows =
        executeQuery(
            "SELECT title "
                + "FROM [publicdata:samples.wikipedia] LIMIT 10",
            bigquery,
            projectId);

    displayResults(rows);
  }
}
// [END all]
