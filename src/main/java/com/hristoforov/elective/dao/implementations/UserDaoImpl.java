package com.hristoforov.elective.dao.implementations;

import com.hristoforov.elective.DBCPDataSource;
import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.entities.Role;
import com.hristoforov.elective.entities.User;
import com.hristoforov.elective.utils.WorkWithEntityFromDb;
import com.hristoforov.elective.utils.WorkWithFile;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends DBCPDataSource implements UserDao {
    @Override
    public void create(User entity) {

        try (Connection conn = createConnection();
             PreparedStatement ps =
                     conn.prepareStatement(WorkWithFile.readFromFile("userQueries/fillTableUser.sql"),
                             Statement.RETURN_GENERATED_KEYS)) {
            try {
                conn.setAutoCommit(false);
                ps.setString(1, entity.getLastName());
                ps.setString(2, entity.getFirstName());
                ps.setString(3, entity.getLogin());
                ps.setString(4, entity.getPassword());
                ps.setString(5, entity.getEmail());
                ps.setString(6, entity.getRole().name());
                ps.execute();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User entity) {
        try (Connection conn = createConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement(WorkWithFile.readFromFile("userQueries/updateUser.sql"))) {
            try {
                conn.setAutoCommit(false);
                preparedStatement.setString(1, entity.getLastName());
                preparedStatement.setString(2, entity.getFirstName());
                preparedStatement.setString(3, entity.getLogin());
                preparedStatement.setString(4, entity.getPassword());
                preparedStatement.setString(5, entity.getEmail());
                preparedStatement.setString(6, entity.getRole().name());
                preparedStatement.setLong(7, entity.getId());

                preparedStatement.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void remove(User entity) {
        try (Connection conn = createConnection();
             PreparedStatement statement =
                     conn.prepareStatement(WorkWithFile.readFromFile("userQueries/removeUser.sql"))) {
            try {
                conn.setAutoCommit(false);
                statement.setLong(1, entity.getId());
                statement.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> listUser = new ArrayList<>();

        try (Connection conn = createConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement(WorkWithFile.readFromFile("userQueries/findAllInUser.sql"))) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    listUser.add(WorkWithEntityFromDb.createUserFromDb(resultSet));
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }

        return listUser;
    }

    @Override
    public User findById(Long id) {
        try (Connection conn = createConnection();
             PreparedStatement statement =
                     conn.prepareStatement(WorkWithFile.readFromFile("userQueries/findUserById.sql"))) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return WorkWithEntityFromDb.createUserFromDb(resultSet);
                }

                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findByLogin(String login) {
        try (Connection conn = createConnection();
             PreparedStatement statement =
                     conn.prepareStatement(WorkWithFile.readFromFile("userQueries/findUserByLogin.sql"))) {

            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return WorkWithEntityFromDb.createUserFromDb(resultSet);
                }

                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findByEmail(String email) {
        try (Connection conn = createConnection();
             PreparedStatement statement =
                     conn.prepareStatement(WorkWithFile.readFromFile("userQueries/findUserByEmail.sql"))) {

            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return WorkWithEntityFromDb.createUserFromDb(resultSet);
                }

                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
