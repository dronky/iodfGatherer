import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;
import java.util.Objects;

// https://www.eetimes.com/author.asp?section_id=216&doc_id=1285465 - EBCDIC encoding

public class Server {

    /**
     * @param args
     * @throws InterruptedException
     */

    public static void main(String[] args) throws InterruptedException {
//  стартуем сервер на порту 3345
        StringBuilder line = new StringBuilder();
        try (ServerSocket server = new ServerSocket(3344)) {
// становимся в ожидание подключения к сокету под именем - "client" на серверной стороне                                
            Socket client = server.accept();

// после хэндшейкинга сервер ассоциирует подключающегося клиента с этим сокетом-соединением             
            System.out.print("Connection accepted.");

// инициируем каналы для  общения в сокете, для сервера     

// канал записи в сокет
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            System.out.println("DataOutputStream  created");

            // канал чтения из сокета
            DataInputStream in = new DataInputStream(client.getInputStream());
            InputStreamReader rdr = new InputStreamReader(in, java.nio.charset.Charset.forName("IBM1047"));
            BufferedReader buffer = new BufferedReader(rdr);
            System.out.println("DataInputStream created");

            if (client.isConnected()) {
                writeMessage("OK",out);
            }

// начинаем диалог с подключенным клиентом в цикле, пока сокет не закрыт                
            while (!client.isClosed()) {
                line.setLength(0);

                System.out.println("Server reading from channel");

// readLine waiting newline char ('15'x in EBCDIC)
// so if client not sending newline char, server hangs
                    while (Objects.isNull(line.toString()) || line.toString().equalsIgnoreCase("")){
                        line.append(buffer.readLine());
                    }

                    if (line.toString().trim().equalsIgnoreCase("KEY:gsp4gg4393j1g0")){
                        System.out.println("client is trusted");
                        continue;
                    }

                    System.out.println("LINE:" + line);
                    writeMessage("Response from server. Current time:" + Instant.now() ,out);

// инициализация проверки условия продолжения работы с клиентом по этому сокету по кодовому слову       - quit
                    if (line.toString().equalsIgnoreCase("quit") || line.toString().equalsIgnoreCase("null")) {
                        System.out.println("Client initialize connections suicide ...");
                        out.writeUTF("Server reply - " + line + " - OK");
                        out.flush();
                        Thread.sleep(3000);
                        break;
                }
            }

// если условие выхода - верно выключаем соединения             
            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");

            // закрываем сначала каналы сокета !
            in.close();
            out.close();

            // потом закрываем сам сокет общения на стороне сервера!
            client.close();

            // потом закрываем сокет сервера который создаёт сокеты общения
            // хотя при многопоточном применении его закрывать не нужно
            // для возможности поставить этот серверный сокет обратно в ожидание нового подключения

            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void writeMessage(String msg, DataOutputStream out) throws IOException {
        // Подтвердить подключение клиента
        out.write(msg.getBytes("CP037")); // get bytes for EBCDIC codepage 37
        System.out.println("SERVER MESSAGE: " + msg + " SENT");
        // освобождаем буфер сетевых сообщений (по умолчанию сообщение не сразу отправляется в сеть, а сначала накапливается в специальном буфере сообщений, размер которого определяется конкретными настройками в системе, а метод  - flush() отправляет сообщение не дожидаясь наполнения буфера согласно настройкам системы
        out.flush();
    }

}
