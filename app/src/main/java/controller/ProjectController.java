/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;

/**
 *
 * @author felipe
 */
public class ProjectController {
    
    public void save(Project project) {
        String sql = "INSERT INTO projects (name, "
                + "description, "
                + "createdAt, " 
                + "updatedAt) VALUES (?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
            try {
                connection = ConnectionFactory.getConnection();
                statement = connection.prepareStatement(sql);
                statement.setString(1, project.getName());
                statement.setString(2, project.getDescription());
                statement.setDate(3, new Date(project.getCreatedAt().getTime()));
                statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
                statement.execute();
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao salvar projeto", ex);
            } finally {
                ConnectionFactory.closeConnection(connection, statement);
            }
    }
    
    public void update(Project project) {
        String sql = "UPDATE projects SET "
                + "name = ?, "
                + "description = ?, "
                + "createdAt = ?, "
                + "updatedAt = ?, "
                + "WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //Estabelecendo a conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //Preparando a query
            statement = connection.prepareStatement(sql);
            
            //Setando os valores do statement
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());
            
            //Executando a query
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar projeto" + ex.getMessage(), ex);
        } finally {
                ConnectionFactory.closeConnection(connection, statement);
            }
    }
    
    public void removeById(int projectId) throws SQLException {
    
        String sql = "DELETE FROM projects Where id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, projectId);
            statement.execute();       
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar a projeto" + ex.getMessage(), ex);        
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public List<Project> getAll() {
        String sql = "SELECT * FROM projects";
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        //Lista de projetos que será devolvida quando a chamada do método acontecer
        List<Project> projects = new ArrayList<>();
        
        try {
            //Criação da conexão
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            //Valor retornado pela execução da query
            resultSet = statement.executeQuery();
            
            //Enquanto houverem valores a ser percorridos no meu ResultSet
            while (resultSet.next()) {
                
                Project project = new Project();
                
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("CreatedAt"));
                project.setUpdatedAt(resultSet.getDate("updatedAt"));
                
                projects.add(project);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao buscar projetos" + ex.getMessage(), ex);            
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        
        //Lista de projetos que foi criada e carregada do bando de dados
        return projects;
    }
}
