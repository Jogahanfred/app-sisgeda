[1mdiff --git a/src/main/java/pe/mil/fap/common/constants/Configuracion.java b/src/main/java/pe/mil/fap/common/constants/Configuracion.java[m
[1mindex ea035d1..51d8fba 100644[m
[1m--- a/src/main/java/pe/mil/fap/common/constants/Configuracion.java[m
[1m+++ b/src/main/java/pe/mil/fap/common/constants/Configuracion.java[m
[36m@@ -5,7 +5,6 @@[m [mpublic class Configuracion {[m
 	public static class Helper {[m
 		public final static int ANIOS_ATRAS_HISTORIAL = 3; [m
 		public final static String USUARIO_LOGEADO = "usuarioLogeado";[m
[31m-		public final static String ROL_LOGEADO = "rolLogeado";[m
 	}[m
 [m
 }[m
[1mdiff --git a/src/main/java/pe/mil/fap/common/enums/EstandarEnum.java b/src/main/java/pe/mil/fap/common/enums/EstandarEnum.java[m
[1mdeleted file mode 100644[m
[1mindex 221ffa2..0000000[m
[1m--- a/src/main/java/pe/mil/fap/common/enums/EstandarEnum.java[m
[1m+++ /dev/null[m
[36m@@ -1,36 +0,0 @@[m
[31m-package pe.mil.fap.common.enums;[m
[31m-[m
[31m-public enum EstandarEnum {[m
[31m-	DEMOSTRATIVO("D", 1), [m
[31m-	INSUFICIENTE("I", 2),[m
[31m-	REGULAR("R", 3),[m
[31m-	BUENO("B", 4),[m
[31m-	EXCELENTE("E", 5),[m
[31m-	SIN_ESTANDAR("SE", 0),[m
[31m-	PELIGROSO("P", 0),[m
[31m-	NO_REGISTRADA("NR", 0);[m
[31m-[m
[31m-	private final String codigo;[m
[31m-	private final Integer nivel;[m
[31m-[m
[31m-	private EstandarEnum(String codigo, Integer nivel) {[m
[31m-		this.codigo = codigo;[m
[31m-		this.nivel = nivel;[m
[31m-	}[m
[31m-[m
[31m-	public Integer getNivel() {[m
[31m-		return nivel;[m
[31m-	}[m
[31m-[m
[31m-	public String getCodigo() {[m
[31m-		return codigo;[m
[31m-	}[m
[31m-    public static int getNivelPorCodigo(String codigo) {[m
[31m-        for (EstandarEnum c : values()) {[m
[31m-            if (c.getCodigo().equals(codigo)) {[m
[31m-                return c.getNivel();[m
[31m-            }[m
[31m-        }[m
[31m-        return -1; [m
[31m-    }[m
[31m-}[m
[1mdiff --git a/src/main/java/pe/mil/fap/common/enums/ManiobraEstadoEnum.java b/src/main/java/pe/mil/fap/common/enums/ManiobraEstadoEnum.java[m
[1mdeleted file mode 100644[m
[1mindex 186c608..0000000[m
[1m--- a/src/main/java/pe/mil/fap/common/enums/ManiobraEstadoEnum.java[m
[1m+++ /dev/null[m
[36m@@ -1,6 +0,0 @@[m
[31m-package pe.mil.fap.common.enums;[m
[31m-[m
[31m-public enum ManiobraEstadoEnum {[m
[31m-	SOBRE_ESTANDAR, BAJO_ESTANDAR, NORMAL;[m
[31m-	[m
[31m-}[m
[1mdiff --git a/src/main/java/pe/mil/fap/common/enums/RolEnum.java b/src/main/java/pe/mil/fap/common/enums/RolEnum.java[m
[1mdeleted file mode 100644[m
[1mindex f482f53..0000000[m
[1m--- a/src/main/java/pe/mil/fap/common/enums/RolEnum.java[m
[1m+++ /dev/null[m
[36m@@ -1,12 +0,0 @@[m
[31m-package pe.mil.fap.common.enums;[m
[31m- [m
[31m-public enum RolEnum {[m
[31m-	ROLE_ADMIN,[m
[31m-	ROLE_COESC,[m
[31m-	ROLE_JEOPE,[m
[31m-	ROLE_JEINS,[m
[31m-	ROLE_INSTR,[m
[31m-	ROLE_ALUMN,[m
[31m-	ROLE_USER;[m
[31m-[m
[31m-}[m
[1mdiff --git a/src/main/java/pe/mil/fap/common/utils/UtilHelpers.java b/src/main/java/pe/mil/fap/common/utils/UtilHelpers.java[m
[1mindex 1ee1b8d..f1130e4 100644[m
[1m--- a/src/main/java/pe/mil/fap/common/utils/UtilHelpers.java[m
[1m+++ b/src/main/java/pe/mil/fap/common/utils/UtilHelpers.java[m
[36m@@ -2,7 +2,6 @@[m [mpackage pe.mil.fap.common.utils;[m
 [m
 import java.text.DateFormat;[m
 import java.text.SimpleDateFormat;[m
[31m-import java.time.LocalTime;[m
 import java.util.Date;[m
 import java.util.List;[m
 [m
[36m@@ -63,15 +62,4 @@[m [mpublic final class UtilHelpers {[m
 		return errorMessage;[m
 [m
 	}[m
[31m-	[m
[31m-	public static String getGreetingTimeOfDay() {[m
[31m-		LocalTime now = LocalTime.now();[m
[31m-        if (now.isBefore(LocalTime.of(12, 0))) {[m
[31m-            return "Buenos Días"; // 00:00 hasta 11:59[m
[31m-        } else if (now.isBefore(LocalTime.of(18, 0))) {[m
[31m-            return "Buenas Tardes"; // 12:00 hasta 18:00[m
[31m-        } else {[m
[31m-            return "Buenas Noches"; // 18:01 hasta 23:59[m
[31m-        }[m
[31m-	}[m
 }[m
[1mdiff --git a/src/main/java/pe/mil/fap/configuration/filter/SessionFilter.java b/src/main/java/pe/mil/fap/configuration/filter/SessionFilter.java[m
[1mindex d47dbe0..6f35d1f 100644[m
[1m--- a/src/main/java/pe/mil/fap/configuration/filter/SessionFilter.java[m
[1m+++ b/src/main/java/pe/mil/fap/configuration/filter/SessionFilter.java[m
[36m@@ -1,8 +1,6 @@[m
 package pe.mil.fap.configuration.filter;[m
 [m
 import java.io.IOException;[m
[31m-import java.util.ArrayList;[m
[31m-import java.util.List;[m
 [m
 import org.springframework.stereotype.Component;[m
 [m
[36m@@ -14,7 +12,6 @@[m [mimport jakarta.servlet.ServletResponse;[m
 import jakarta.servlet.http.HttpServletRequest;[m
 import jakarta.servlet.http.HttpServletResponse;[m
 import pe.mil.fap.common.constants.Configuracion;[m
[31m-import pe.mil.fap.model.security.RolDTO;[m
 import pe.mil.fap.model.security.UsuarioDTO;[m
 [m
 @Component[m
[36m@@ -24,27 +21,12 @@[m [mpublic class SessionFilter implements Filter {[m
 	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {[m
 		HttpServletRequest request = (HttpServletRequest) req;[m
 		HttpServletResponse response = (HttpServletResponse) res;[m
[31m-		[m
[31m-		List<RolDTO> lstRoles = new ArrayList<>();[m
[31m-		lstRoles.add(new RolDTO(1, "ROLE_ADMIN", "ADMINISTRADOR"));[m
[31m-		lstRoles.add(new RolDTO(2, "ROLE_JEOPE", "JEFE DE OPERACIONES"));[m
[31m-		lstRoles.add(new RolDTO(3, "ROLE_COESC", "COMANDO DE ESCUA	DRON"));[m
[31m-		lstRoles.add(new RolDTO(4, "ROLE_JEINS", "JEFE DE INSTRUCCION"));[m
[31m-		//TTAA[m
[31m-		lstRoles.add(new RolDTO(5, "ROLE_INSTR", "INSTRUCTOR"));[m
[31m-		lstRoles.add(new RolDTO(6, "ROLE_ALUMN", "ALUMNO"));[m
[31m-		[m
[31m-		lstRoles.add(new RolDTO(7, "ROLE_USER", "USUARIO CONSULTA"));[m
[31m-		[m
[31m-		//Esto me devolvera mi usuario, (Usuario / Contraseña)[m
[32m+[m[41m			[m
 		UsuarioDTO usuarioLogeado = UsuarioDTO.builder().idUnidad(5)//283(ALAR1) 5(GRU51), 144(ALAR6), 1(COFAP), 323(GRUP7)[m
[31m-														.noNombre("Rosmery Rivero")[m
[31m-														.coNsa("97177") [m
[31m-														.idEscuadron(1)//1, 510[m
[31m-														.lstRoles(lstRoles)[m
[32m+[m														[32m.noNombre("USUARIO COMOP")[m
 														.build();[m
[31m-		if (usuarioLogeado != null) { [m
[31m-			request.getSession().setAttribute(Configuracion.Helper.USUARIO_LOGEADO, usuarioLogeado); [m
[32m+[m		[32mif (usuarioLogeado != null) {[m
[32m+[m			[32mrequest.getSession().setAttribute(Configuracion.Helper.USUARIO_LOGEADO, usuarioLogeado);[m
 		} [m
 		chain.doFilter(request, response);[m
 	}[m
[1mdiff --git a/src/main/java/pe/mil/fap/controller/CalificacionController.java b/src/main/java/pe/mil/fap/controller/CalificacionController.java[m
[1mindex b6e1746..aaf248f 100644[m
[1m--- a/src/main/java/pe/mil/fap/controller/CalificacionController.java[m
[1m+++ b/src/main/java/pe/mil/fap/controller/CalificacionController.java[m
[36m@@ -1,8 +1,6 @@[m
 package pe.mil.fap.controller; [m
 [m
 import java.util.List;[m
[31m-import java.util.Map;[m
[31m-import java.util.Optional;[m
 [m
 import org.springframework.stereotype.Controller;[m
 import org.springframework.web.bind.annotation.GetMapping;[m
[36m@@ -13,14 +11,9 @@[m [mimport org.springframework.web.bind.annotation.RequestParam;[m
 import org.springframework.web.bind.annotation.ResponseBody;[m
 [m
 import jakarta.servlet.http.HttpServletRequest;[m
[31m-import pe.mil.fap.common.constants.Configuracion;[m
[31m-import pe.mil.fap.common.enums.RolEnum;[m
[31m-import pe.mil.fap.model.administration.MiembroDTO;[m
[31m-import pe.mil.fap.model.administration.SubFaseDTO;[m
 import pe.mil.fap.model.helpers.InscripcionMisionDTO;[m
 import pe.mil.fap.model.helpers.MessageDTO;[m
[31m-import pe.mil.fap.model.helpers.RegistroCalificacionDTORequest;[m
[31m-import pe.mil.fap.model.security.Usuar