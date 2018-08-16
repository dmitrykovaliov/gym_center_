package com.dk.gym.dao.impl;

import com.dk.gym.dao.TrainerDao;
import com.dk.gym.entity.Trainer;
import com.dk.gym.exception.DaoException;
import com.dk.gym.pool.ConnectionPool;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static com.dk.gym.dao.DatabaseConstant.*;

/**
 * The Class TrainerDaoImpl. Implementation of trainer dao.
 */
public class TrainerDaoImpl extends TrainerDao {

    /** The Constant SQL_INSERT_TRAINER. */
    private static final String SQL_INSERT_TRAINER = "INSERT INTO trainer(id_trainer, tr_name, tr_lastname, " +
            "tr_phone, tr_personal_data, tr_iconpath) " +
            "VALUES (NULL, ?, ?, ?, ?, ?)";
    
    /** The Constant SQL_UPDATE_TRAINER. */
    private static final String SQL_UPDATE_TRAINER = "UPDATE trainer SET tr_name = ?, tr_lastname = ?, tr_phone = ?, " +
            "tr_personal_data = ?, tr_iconpath = ? " +
            "WHERE id_trainer = ?";
    
    /** The Constant SQL_UPDATE_TRAINER_USERID. */
    private static final String SQL_UPDATE_TRAINER_USERID = "UPDATE trainer SET id_user = ? " +
            "WHERE id_trainer = ?";
    
    /** The Constant SQL_SELECT_ALL_TRAINER. */
    private static final String SQL_SELECT_ALL_TRAINER = "SELECT id_trainer, tr_name, tr_lastname, tr_phone," +
            "tr_personal_data, tr_iconpath FROM trainer " +
            "ORDER BY id_trainer";
    
    /** The Constant SQL_SELECT_TRAINER_BY_ID. */
    private static final String SQL_SELECT_TRAINER_BY_ID = "SELECT id_trainer, tr_name, tr_lastname, tr_phone," +
            "tr_personal_data, tr_iconpath FROM trainer WHERE id_trainer = ?";
    
    /** The Constant SQL_SELECT_TRAINER_BY_USERID. */
    private static final String SQL_SELECT_TRAINER_BY_USERID = "SELECT id_trainer, tr_name, tr_lastname, tr_phone," +
            "tr_personal_data, tr_iconpath FROM trainer WHERE id_user = ?";
    
    /** The Constant SQL_DELETE_TRAINER. */
    private static final String SQL_DELETE_TRAINER = "DELETE FROM trainer WHERE id_trainer = ?";

    /**
     * Instantiates a new trainer dao impl.
     */
    public TrainerDaoImpl() {
        connection = ConnectionPool.getInstance().receiveConnection();
    }

    /* (non-Javadoc)
     * @see com.dk.gym.dao.AbstractDao#create(com.dk.gym.entity.Entity)
     */
    @Override
    public int create(Trainer entity) throws DaoException {

        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_TRAINER,
                Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getPhone());
            statement.setString(4, entity.getPersonalData());
            statement.setString(5, entity.getIconPath());

            statement.executeUpdate();

            try (ResultSet generatedKey = statement.getGeneratedKeys()) {

                if (generatedKey.next()) {
                    return generatedKey.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not created: ", e);
        }
        return RETURNED_NEGATIVE_RESULT;
    }

    /* (non-Javadoc)
     * @see com.dk.gym.dao.AbstractDao#update(com.dk.gym.entity.Entity)
     */
    @Override
    public boolean update(Trainer entity) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_TRAINER)) {

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getPhone());
            statement.setString(4, entity.getPersonalData());
            statement.setString(5, entity.getIconPath());
            statement.setInt(6, entity.getIdTrainer());

            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new DaoException("Not updated: ", e);
        }
    }

    /* (non-Javadoc)
     * @see com.dk.gym.dao.TrainerDao#updateUserId(java.lang.Integer, int)
     */
    @Override
    public boolean updateUserId(Integer idUser, int idTrainer) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_TRAINER_USERID)) {

            statement.setObject(1, idUser);
            statement.setInt(2, idTrainer);

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            throw new DaoException("Not updated by userId: ", e);
        }
    }

    /* (non-Javadoc)
     * @see com.dk.gym.dao.AbstractDao#findAll()
     */
    @Override
    public List<Trainer> findAll() throws DaoException {

        List<Trainer> list = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_TRAINER)) {

                while (resultSet.next()) {

                    Trainer trainer = new Trainer();

                    trainer.setIdTrainer(resultSet.getInt(ID_TRAINER));
                    trainer.setName(resultSet.getString(TR_NAME));
                    trainer.setLastName(resultSet.getString(TR_LASTNAME));
                    trainer.setPhone(resultSet.getString(TR_PHONE));
                    trainer.setPersonalData(resultSet.getString(TR_PERSONAL_DATA));
                    trainer.setIconPath(resultSet.getString(TR_ICONPATH));

                    list.add(trainer);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found all: ", e);
        }
        return list;
    }


    /* (non-Javadoc)
     * @see com.dk.gym.dao.TrainerDao#findById(int)
     */
    @Override
    public Trainer findById(int id) throws DaoException {

        Trainer trainer = new Trainer();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TRAINER_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();

                    trainer.setIdTrainer(resultSet.getInt(ID_TRAINER));
                    trainer.setName(resultSet.getString(TR_NAME));
                    trainer.setLastName(resultSet.getString(TR_LASTNAME));
                    trainer.setPhone(resultSet.getString(TR_PHONE));
                    trainer.setPersonalData(resultSet.getString(TR_PERSONAL_DATA));
                    trainer.setIconPath(resultSet.getString(TR_ICONPATH));
            }
        } catch (SQLException e) {
            throw new DaoException("Not found by ID: ", e);
        }
        return trainer;
    }

    /* (non-Javadoc)
     * @see com.dk.gym.dao.TrainerDao#delete(int)
     */
    @Override
    public boolean delete(int id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_TRAINER)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.dk.gym.dao.TrainerDao#findByUserId(int)
     */
    @Override
    public Trainer findByUserId(int idUser) throws DaoException {
        Trainer trainer = new Trainer();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TRAINER_BY_USERID)) {
            statement.setInt(1, idUser);

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();

                trainer.setIdTrainer(resultSet.getInt(ID_TRAINER));
                trainer.setName(resultSet.getString(TR_NAME));
                trainer.setLastName(resultSet.getString(TR_LASTNAME));
                trainer.setPhone(resultSet.getString(TR_PHONE));
                trainer.setPersonalData(resultSet.getString(TR_PERSONAL_DATA));
                trainer.setIconPath(resultSet.getString(TR_ICONPATH));
            }
        } catch (SQLException e) {
            throw new DaoException("Not found byUserId: ", e);
        }
        return trainer;
    }
}
