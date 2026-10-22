package psp.ut1;

public class InfoProcesos {
    public static void main(String[] args) {
        ProcessHandle actual = ProcessHandle.current();
        System.out.println("PID actual: " + actual.pid());
        actual.parent().ifPresent(p -> System.out.println("PID del padre: " + p.pid()));
        ProcessHandle.Info info = actual.info();
        System.out.println("Comando: " + info.command().orElse("desconocido"));
        System.out.println("Usuario: " + info.user().orElse("desconocido"));
        System.out.println("Inicio:  " + info.startInstant().map(Object::toString).orElse("?"));
        System.out.println("Núcleos: " + Runtime.getRuntime().availableProcessors());
        long javas = ProcessHandle.allProcesses()
                .filter(ph -> ph.info().command().orElse("").contains("java"))
                .count();
        System.out.println("Procesos Java en ejecución: " + javas);
    }
}
