package executors;

/**
 * Errors And Warnings.
 *
 * @author Parisi Germán
 * @version 1.1
 */
public class EAW {

    public static String error = "error";
    public static String errorInfo = "error_info";
    public static final int ERRORS_COUNT = 12;
    public static final int WARNINGS_COUNT = 2;

    public static class ERR {

        public static class NOT_EXISTS_GROUP {

            public static int getError() {
                return 1;
            }

            public static String getInfo(String idGroup) {
                return "No existe el grupo " + idGroup;
            }
        }

        public static class NOT_CLIENT_IN_GROUP {

            public static int getError() {
                return 2;
            }

            public static String getInfo(String idClient, String idGroup) {
                return "No existe el cliente " + idClient + " en el grupo " + idGroup;
            }
        }

        public static class GROUP_IS_FULL {

            public static int getError() {
                return 3;
            }

            public static String getInfo(String idGroup) {
                return "El grupo está lleno " + idGroup;
            }
        }

        public static class DUPLICATE_IN_GROUP {

            public static int getError() {
                return 4;
            }

            public static String getInfo() {
                return "El grupo no acepta duplicados";
            }
        }

        public static class NOT_PASSWORD {

            public static int getError() {
                return 5;
            }

            public static String getInfo() {
                return "Falta clave";
            }
        }

        public static class INVALID_KEY {

            public static int getError() {
                return 6;
            }

            public static String getInfo() {
                return "La clave es incorrecta: ";
            }
        }

        public static class NOT_EXISTS_CLIENT {

            public static int getError() {
                return 7;
            }

            public static String getInfo(String idCliente) {
                return "No existe el cliente: " + idCliente;
            }
        }

        public static class INVALID_COUNT_ATTRIBUTE {

            public static int getError() {
                return 8;
            }

            public static String getInfo() {
                return "Incorrecto valor de Cantidad de Atributos ";
            }
        }

        public static class NOT_EXISTS_KEY {

            public static int getError() {
                return 9;
            }

            public static String getInfo(String key) {
                return "No existe la clave " + key;
            }
        }

        public static class INCORRECT_LENGTH_KEY_VALUE {

            public static int getError() {
                return 10;
            }

            public static String getInfo() {
                return "La cantidad de clave no coincide con la cantidad de valores";
            }
        }

        public static class MISSPELLED {

            public static int getError() {
                return 11;
            }

            public static String getInfo(String cmd) {
                return "Comando mal escrito: '" + cmd + "'";
            }
        }

        public static class NOT_EXISTS_CLIENT_IN_GROUP {

            public static int getError() {
                return 11;
            }

            public static String getInfo(String idClient, String idGroup) {
                return "No existe el cliente '" + idClient + "' en el grupo '" + idGroup + "'";
            }
        }
    }

    public static class WAR {

        public static class INVALID_CLIENT {

            public static int getWarning() {
                return 1;
            }

            public static String getInfo(String idClient) {
                return "Cliente inválido: " + idClient;
            }
        }

        public static class NOT_EXISTS_CLIENT_IN_GROUP {

            public static int getWarning() {
                return 2;
            }

            public static String getInfo(String idClient, String idGroup) {
                return "No existe el cliente " + idClient + " en el grupo " + idGroup;
            }
        }
    }
}
