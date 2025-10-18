import SubsecuenciaMasLarga._

// ---------------------------------------------------------------------------
// 1) subindices(i, n)
// ---------------------------------------------------------------------------
subindices(2, 2)                 // => Set(List())                      // caso base (i >= n)
subindices(2, 3)                 // => Set(List(), List(2))
subindices(0, 1)                 // => Set(List(), List(0))
subindices(0, 3)                 // => Set(List(), List(0), List(1), List(2), List(0,1), List(0,2), List(1,2), List(0,1,2))
subindices(1, 4)                 // => Set(List(1), List(1, 2, 3), List(1, 3), List(3), List(), List(2, 3), List(1, 2), List(2))

// ---------------------------------------------------------------------------
// 2) subSecuenciaAsoc(s, inds)
// ---------------------------------------------------------------------------
val s = Seq(20, 30, 10, 40, 15, 16, 17)
subSecuenciaAsoc(s, Seq())             // => List()                      // índices vacíos -> subsecuencia vacía
subSecuenciaAsoc(s, Seq(0, 2, 4))      // => List(20, 10, 15)
subSecuenciaAsoc(s, Seq(1, 2, 4, 6))   // => List(30, 10, 15, 17)
subSecuenciaAsoc(s, 0 until s.length)  // => List(20, 30, 10, 40, 15, 16, 17)  // subsecuencia = toda la secuencia
subSecuenciaAsoc(s, Seq(3))            // => List(40)                   // un solo índice

// ---------------------------------------------------------------------------
// 3) subSecuenciasDe(s)     (todas las subsecuencias; incluye la vacía)
// ---------------------------------------------------------------------------
subSecuenciasDe(Seq())                 // => Set(List())                 // solo la vacía
subSecuenciasDe(Seq(5))                // => Set(List(), List(5))
subSecuenciasDe(Seq(1, 2))             // => Set(List(), List(2), List(1), List(1, 2))
subSecuenciasDe(Seq(20, 30, 10))       // => Set(List(30), List(20, 30, 10), List(30, 10), List(20), List(10), List(20, 30), List(20, 10), List())
subSecuenciasDe(Seq(1, 1))             // => Set(List(), List(1), List(1, 1))   // dos [1] distintos colapsan a uno en Set

// ---------------------------------------------------------------------------
// 4) incremental(ss)   (estrictamente creciente)
// ---------------------------------------------------------------------------
incremental(Seq())                     // => true                        // convención: vacía es incremental
incremental(Seq(10))                   // => true                        // un elemento
incremental(Seq(1, 2, 3))             // => true
incremental(Seq(2, 2))                 // => false                       // no es estricta
incremental(Seq(3, 2, 4))             // => false                       // baja y luego sube, pero ya falló

// ---------------------------------------------------------------------------
// 5) subSecuenciasInc(s)   (todas las subsecuencias incrementales de s)
// ---------------------------------------------------------------------------
subSecuenciasInc(Seq())                // => Set(List())                 // la vacía cuenta
subSecuenciasInc(Seq(2))               // => Set(List(), List(2))
subSecuenciasInc(Seq(1, 2))            // => Set(List(), List(2), List(1), List(1, 2))
subSecuenciasInc(Seq(2, 1))            // => Set(List(), List(1), List(2))       // [2,1] no es incremental
subSecuenciasInc(Seq(2, 2))            // => Set(List(), List(2))                 // [2,2] no es estricta

// ---------------------------------------------------------------------------
// 6) subsecuenciaIncrementalMasLarga(s)   (LIS por fuerza bruta)
//   Usamos casos con LIS única para evitar empates.
// ---------------------------------------------------------------------------
subsecuenciaIncrementalMasLarga(Seq())                   // => List()
subsecuenciaIncrementalMasLarga(Seq(7))                  // => List(7)
subsecuenciaIncrementalMasLarga(Seq(20,30,10,40,15,16,17)) // => List(10, 15, 16, 17)
subsecuenciaIncrementalMasLarga(Seq(5, 1, 2, 3, 0))      // => List(1, 2, 3)
subsecuenciaIncrementalMasLarga(Seq(1, 2, 3, 4))         // => List(1, 2, 3, 4)

// ---------------------------------------------------------------------------
// 7) ssimlComenzandoEn(i, s)   (LIS que comienza en s(i))
// ---------------------------------------------------------------------------
val t = Seq(10,9,8,7,6,5,4,3,2,1,22,21,20,19,18,17,16,15,14,13,12,11)
ssimlComenzandoEn(4, t)                                    // => List(6, 22)
ssimlComenzandoEn(12, t)                                   // => List(20)
ssimlComenzandoEn(-1, t)                                   // => List()        // índice inválido
ssimlComenzandoEn(100, t)                                  // => List()        // índice inválido
ssimlComenzandoEn(2, s)                                    // => List(10, 15, 16, 17)   // empieza en 10 (índice 2) de s

// ---------------------------------------------------------------------------
// 8) subSecIncMasLargaV2(s)   (versión por subproblemas; más eficiente)
// ---------------------------------------------------------------------------
subSecIncMasLargaV2(Seq())                                 // => List()
subSecIncMasLargaV2(s)                                     // => List(10, 15, 16, 17)
subSecIncMasLargaV2(Seq(10, 9, 8, 7, 6))                   // => List(10)      // estrictamente decreciente: LIS de tamaño 1 (elige la primera)
subSecIncMasLargaV2(Seq(1, 2, 3, 4, 5))                    // => List(1, 2, 3, 4, 5)
subSecIncMasLargaV2(Seq(3, 1, 2, 1, 2, 3))                 // => List(1, 2, 3)
