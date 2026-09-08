/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifba.clinica.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/clinica_medica";

    private static final String USUARIO = "postgres";
    private static final String SENHA = "gta6emnovembro";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                URL,
                USUARIO,
                SENHA
        );
    }
}