import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/skincare;create=true";
    private static final String USER = "skincare";
    private static final String PASSWORD = "skincare";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void inserirDadosFormulario(String nome, String email, String tipo_de_pele, String protetor_solar,
                                              String alergia, String alergia_informe, String rotinas_cuidados,
                                              String melasma_familia, String contraindicacao) {
        try (Connection connection = getConnection()) {
            String query = "INSERT INTO skin_care_form (nome, email, tipo_de_pele, protetor_solar, alergia, alergia_informe, "
                    + " rotinas_cuidados, melasma_familia, contraindicacao) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nome);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, tipo_de_pele);
                preparedStatement.setString(4, protetor_solar);
                preparedStatement.setString(5, alergia);
                preparedStatement.setString(6, alergia_informe);
                preparedStatement.setString(7, rotinas_cuidados);
                preparedStatement.setString(8, melasma_familia);
                preparedStatement.setString(9, contraindicacao);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        inserirDadosFormulario("nome", "email", "tipo_de_pele", "protetor_solar",
                "alergia", "alergia_informe", "rotinas_cuidados", "melasma_familia", "contraindicacao");
    }
}