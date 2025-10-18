package object SubsecuenciaMasLarga {
  type Secuencia    = Seq[Int]
  type Subsecuencia = Seq[Int]


  def subindices(i: Int, n: Int): Set[Seq[Int]] =
    if (i >= n) Set(Seq())
    else {
      val sinI = subindices(i + 1, n)                            // no tomamos i
      val conI = for (resto <- subindices(i + 1, n)) yield i +: resto // tomamos i
      // Mezclamos usando comprensiones (manteniendo al menos un for en esta función)
      (for (x <- sinI) yield x).toSet ++ (for (x <- conI) yield x).toSet
    }

  def subSecuenciaAsoc(s: Secuencia, inds: Seq[Int]): Subsecuencia =
    for (j <- inds) yield s(j)

  def subSecuenciasDe(s: Secuencia): Set[Subsecuencia] =
    (for (inds <- subindices(0, s.length)) yield subSecuenciaAsoc(s, inds)).toSet

  def incremental(ss: Subsecuencia): Boolean = {
    val n = ss.length
    if (n <= 1) true
    else {
      val comparaciones = for (k <- 0 until (n - 1)) yield (ss(k) < ss(k + 1))
      val buenas        = for (b <- comparaciones if b) yield 1
      buenas.length == comparaciones.length
    }
  }

  def subSecuenciasInc(s: Secuencia): Set[Subsecuencia] =
    (for (ss <- subSecuenciasDe(s) if incremental(ss)) yield ss).toSet

  def subsecuenciaIncrementalMasLarga(s: Secuencia): Subsecuencia = {
    val cands = subSecuenciasInc(s).toSeq
    if (cands.isEmpty) Seq()
    else {

      val lens = for (i <- 0 until cands.length) yield (i, cands(i).length)
      val iMax = (for (p <- lens) yield p).maxBy(_._2)._1
      cands(iMax)
    }
  }

  def ssimlComenzandoEn(i: Int, s: Secuencia): Subsecuencia = {
    if (i < 0 || i >= s.length) Seq()
    else {
      val candidatos =
        for {
          j <- (i + 1) until s.length
          if s(j) > s(i)
        } yield ssimlComenzandoEn(j, s)

      val pares = for (k <- 0 until candidatos.length) yield (k, candidatos(k).length)
      val mejorSufijo =
        if (pares.isEmpty) Seq()
        else candidatos((for (p <- pares) yield p).maxBy(_._2)._1)

      s(i) +: mejorSufijo
    }
  }

  def subSecIncMasLargaV2(s: Secuencia): Subsecuencia = {
    if (s.isEmpty) Seq()
    else {
      val cands = for (i <- 0 until s.length) yield ssimlComenzandoEn(i, s)
      val lens  = for (i <- 0 until cands.length) yield (i, cands(i).length)
      val iMax  = (for (p <- lens) yield p).maxBy(_._2)._1
      cands(iMax)
    }
  }
}