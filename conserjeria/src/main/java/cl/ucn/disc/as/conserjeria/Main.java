package cl.ucn.disc.as.conserjeria;

import cl.ucn.disc.as.conserjeria.model.Contrato;
import cl.ucn.disc.as.conserjeria.model.Departamento;
import cl.ucn.disc.as.conserjeria.model.Edificio;
import cl.ucn.disc.as.conserjeria.model.Persona;
import cl.ucn.disc.as.conserjeria.services.Sistema;
import cl.ucn.disc.as.conserjeria.services.SistemaImpl;
import cl.ucn.disc.as.conserjeria.util.ValidationUtils;
import io.ebean.DB;
import io.ebean.Database;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Scanner;

/**
 * Main Class.
 *
 * @author Mauricio Godoy-Milla.
 */

@Slf4j
public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Conserjeria AS");
        System.out.println("Menú de opciones:");
        while (true) {
            System.out.println("1) Crear Edificio");
            System.out.println("2) Agregar departamento a edificio");
            System.out.println("3) Asociar dueño a departamento");
            System.out.println("4) Crear contrato");
            System.out.println("5) Listado dueño, departamento");
            System.out.println("0) Salir");

            System.out.print("Seleccione una opción (0-5): ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    crearEdificio(scanner);
                    break;
                case 2:
                    agregarDepartamento(scanner);
                    break;
                case 3:
                    asociarDuenioADepartamento(scanner);
                    break;
                case 4:
                    crearContrato(scanner);
                    break;
                case 5:
                    listarInformacion(scanner);
                    break;
                case 0:
                    System.out.println("Saliendo del programa.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                    break;
            }
        }


    }

    private static void crearEdificio(Scanner scanner) {
        System.out.println("Opción 1: Crear Edificio");

        System.out.print("Ingrese el nombre del edificio: ");
        String nombre = scanner.nextLine().toLowerCase();

        System.out.print("Ingrese la dirección del edificio: ");
        String direccion = scanner.nextLine().toLowerCase();

        Edificio edificio = Edificio.builder()
                .nombre(nombre)
                .direccion(direccion)
                .build();

        Sistema sistema = new SistemaImpl(DB.getDefault());
        sistema.add(edificio);

        System.out.println("Edificio creado con éxito: " + edificio.getNombre());
    }


    private static void agregarDepartamento(Scanner scanner) {
        System.out.println("Opción 2: Agregar departamento a edificio");

        System.out.print("Ingrese el nombre del edificio: ");
        String edificioNombre = scanner.nextLine().toLowerCase();

        Database db = DB.getDefault();

        Edificio edificio = db.find(Edificio.class)
                .where()
                .eq("nombre", edificioNombre)
                .findOne();

        if (edificio != null) {
            System.out.print("Ingrese el número del departamento: ");
            int numero = Integer.parseInt(scanner.nextLine());

            boolean numeroExists = db.find(Departamento.class)
                    .where()
                    .eq("numero", numero)
                    .eq("edificio", edificio)
                    .findOne() != null;

            if (numeroExists) {
                System.out.println("El departamento ya se encuentra agregado.");
            } else {
                System.out.print("Ingrese el piso del departamento: ");
                int piso = Integer.parseInt(scanner.nextLine());

                Departamento departamento = Departamento.builder()
                        .numero(numero)
                        .piso(piso)
                        .edificio(edificio)
                        .build();

                db.save(departamento);
                System.out.println("Departamento asociado al edificio: " + edificioNombre);
            }
        } else {
            System.out.println("El nombre del edificio ingresado no fue encontrado.");
        }
    }

    private static void asociarDuenioADepartamento(Scanner scanner) {
        System.out.println("Opción 3: Asociar dueño a departamento");

        String rut;
        do {
            System.out.print("Ingrese el RUT del dueño: ");
            rut = scanner.nextLine();

            if (!ValidationUtils.isRutValid(rut)) {
                System.out.println("Rut no válido, ingrese nuevamente.");
            }

        } while (!ValidationUtils.isRutValid(rut));

        Database db = DB.getDefault();
        Persona duenio = db.find(Persona.class)
                .where()
                .eq("rut", rut)
                .findOne();

        if (duenio == null) {

            System.out.print("Ingrese el nombre del dueño: ");
            String nombre = scanner.nextLine().toLowerCase();

            System.out.print("Ingrese los apellidos del dueño: ");
            String apellidos = scanner.nextLine().toLowerCase();

            String email;
            do {
                System.out.print("Ingrese el email del dueño: ");
                email = scanner.nextLine();

                if (!ValidationUtils.isEmailValid(email)) {
                    System.out.println("Email no válido, ingrese nuevamente.");
                }

            } while (!ValidationUtils.isEmailValid(email));

            System.out.print("Ingrese el teléfono del dueño: ");
            String telefono = scanner.nextLine();

            try {
                duenio = Persona.builder()
                        .rut(rut)
                        .nombre(nombre)
                        .apellidos(apellidos)
                        .email(email)
                        .telefono(telefono)
                        .build();

                db.save(duenio);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                return;
            }
        }

        System.out.print("Ingrese el nombre del Edificio: ");
        String edificioNombre = scanner.nextLine().toLowerCase();

        Edificio edificio = db.find(Edificio.class)
                .where()
                .eq("nombre", edificioNombre)
                .findOne();

        if (edificio != null) {

            System.out.print("Ingrese el número del departamento: ");
            int numero = Integer.parseInt(scanner.nextLine());


            Departamento departamento = db.find(Departamento.class)
                    .where()
                    .eq("edificio", edificio)
                    .eq("numero", numero)
                    .findOne();

            if (departamento != null) {

                Contrato contrato = Contrato.builder()
                        .persona(duenio)
                        .departamento(departamento)
                        .build();

                db.save(contrato);
                System.out.println("Departamento asociado con éxito.");
            } else {
                System.out.println("El número de departamento no se encontró en el edificio especificado.");
            }
        } else {
            System.out.println("El nombre del edificio ingresado no fue encontrado.");
        }
    }

    private static void crearContrato(Scanner scanner) {
        System.out.println("Opción 4: Crear contrato");

        String rut;
        do {
            System.out.print("Ingrese el RUT del dueño: ");
            rut = scanner.nextLine();

            if (!ValidationUtils.isRutValid(rut)) {
                System.out.println("Rut no válido, ingrese nuevamente.");
            }

        } while (!ValidationUtils.isRutValid(rut));

        Database db = DB.getDefault();
        Persona duenio = db.find(Persona.class)
                .where()
                .eq("rut", rut)
                .findOne();

        if (duenio == null) {
            System.out.print("Ingrese el nombre del dueño: ");
            String nombre = scanner.nextLine().toLowerCase();

            System.out.print("Ingrese los apellidos del dueño: ");
            String apellidos = scanner.nextLine().toLowerCase();

            String email;
            do {
                System.out.print("Ingrese el email del dueño: ");
                email = scanner.nextLine();

                if (!ValidationUtils.isEmailValid(email)) {
                    System.out.println("Email no válido, ingrese nuevamente.");
                }
            } while (!ValidationUtils.isEmailValid(email));

            System.out.print("Ingrese el teléfono del dueño: ");
            String telefono = scanner.nextLine();

            try {
                duenio = Persona.builder()
                        .rut(rut)
                        .nombre(nombre)
                        .apellidos(apellidos)
                        .email(email)
                        .telefono(telefono)
                        .build();

                db.save(duenio);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                return;
            }
        }

        System.out.print("Ingrese el nombre del Edificio: ");
        String edificioNombre = scanner.nextLine().toLowerCase();

        Edificio edificio = db.find(Edificio.class)
                .where()
                .eq("nombre", edificioNombre)
                .findOne();

        if (edificio != null) {
            System.out.print("Ingrese el número del departamento: ");
            int numero = Integer.parseInt(scanner.nextLine());

            Departamento departamento = db.find(Departamento.class)
                    .where()
                    .eq("edificio", edificio)
                    .eq("numero", numero)
                    .findOne();

            if (departamento != null) {
                System.out.print("Ingrese el día de pago (1-30): ");
                int dayOfMonth = Integer.parseInt(scanner.nextLine());

                if (dayOfMonth < 1 || dayOfMonth > 30) {
                    System.out.println("Día de pago no válido. Debe estar entre 1 y 30.");
                    return;
                }

                Instant fechaPago = Instant.now()
                        .atZone(ZoneId.systemDefault())
                        .withDayOfMonth(dayOfMonth)
                        .toInstant();

                Contrato contrato = Contrato.builder()
                        .persona(duenio)
                        .departamento(departamento)
                        .fechaPago(fechaPago)
                        .build();

                db.save(contrato);

                departamento.setContrato(contrato);
                db.save(departamento);

                System.out.println("Contrato creado con éxito.");
            } else {
                System.out.println("El número de departamento no se encontró en el edificio especificado.");
            }
        } else {
            System.out.println("El nombre del edificio ingresado no fue encontrado.");
        }
    }

    private static void listarInformacion(Scanner scanner) {
        System.out.println("Opción 5: Listar información de personas");

        Database db = DB.getDefault();
        List<Persona> personas = db.find(Persona.class).findList();

        for (Persona persona : personas) {

            Long personaId = persona.getId();
            String nombre = persona.getNombre();

            Contrato contrato = db.find(Contrato.class)
                    .where().eq("persona.id", personaId)
                    .findOne();

            if (contrato != null) {
                Departamento departamento = contrato.getDepartamento();

                Integer numero = departamento.getNumero();
                Integer piso = departamento.getPiso();

                System.out.println("Persona Nombre: " + nombre);
                System.out.println("Departamento Numero: " + numero);
                System.out.println("Departamento Piso: " + piso);
                System.out.println();
            }
        }
    }


}

