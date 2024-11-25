package helpers.mq;

import com.rabbitmq.client.*;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
//Требуется доработка класса - объявление exchenge, привязка queue и пр.
public class MqClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(MqClient.class);
    private static ConnectionFactory connectionFactory = new ConnectionFactory();
    private String HOST;
    private Integer PORT;
    private String VIRTUAL_HOST;
    private String USER;
    private String PASSWORD;
    private String exchangeName;
    private String queueName;

    public MqClient(String host, Integer port, String virtualHost, String user, String password) {
        HOST = host;
        PORT = port;
        VIRTUAL_HOST = virtualHost;
        USER = user;
        PASSWORD = password;

        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(user);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        try (Connection connection = connectionFactory.newConnection()) {
            LOGGER.info("Успешно создан клиент Брокера сообщений с заданными параметрами");
        } catch (IOException | TimeoutException e) {
            Assertions.fail("Ошибка при создании клиента БД - проверьте параметры подключения", e);
        }
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    /**
     *
     * @param message
     * @param exchangeName 'null' or exchangeName
     * @param queueName 'null' or queueName
     */
    public void sendMessage(String message, String exchangeName, String queueName) {
        exchangeName = exchangeName == null ? this.exchangeName : exchangeName;
        queueName = queueName == null ? this.queueName : queueName;

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, queueName);
            channel.basicPublish(exchangeName, queueName, null, message.getBytes());
        } catch (IOException | TimeoutException e) {
            Assertions.fail("Ошибка при отправке сообщения", e);
        }
    }

    /**
     *
     * @param queueName'null' or queueName
     * @return текст сообщения
     */
    public String getMessage(String queueName) {
        queueName = queueName == null ? this.queueName : queueName;

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            GetResponse message = channel.basicGet(queueName, true);
            if (message == null) {
                return null;
            } else {
                return new String(message.getBody());
            }
        } catch (IOException | TimeoutException e) {
            Assertions.fail("Ошибка при получении сообщения", e);
        }
        return null;
    }
}
