package dao;

import java.util.List;

import javax.persistence.EntityManager;

import bean.Pessoa;
import connection.ConnectionFactory;

public class PessoaDAO {

   public Pessoa save(Pessoa p) {
      EntityManager em = new ConnectionFactory().getConnection();
      boolean gravou = false;

      try {
         em.getTransaction().begin();

         if (p.getCodigo() == null) {
            em.persist( p );
         } else {
            em.merge( p );
         }

         em.getTransaction().commit();
         gravou = true;
      } catch (Exception e) {
         em.getTransaction().rollback();
         System.err.println( e );
      } finally {
         em.close();
      }

      return gravou ? p : null;
   }

   public Pessoa findById( final Integer id ) {
      Pessoa p = null;

      EntityManager em = new ConnectionFactory().getConnection();

      try {
         p = em.find( Pessoa.class, id);
      } catch (Exception e) {
         System.err.println( e );
      } finally {
         em.close();
      }

      return p;
   }

   @SuppressWarnings( "unchecked" )
   public List<Pessoa> findAll() {
      List<Pessoa> pessoas = null;

      EntityManager em = new ConnectionFactory().getConnection();

      try {
         pessoas = em.createNativeQuery("select * FROM pessoa", Pessoa.class).getResultList();
      } catch (Exception e) {
         System.err.println( e );
      } finally {
         em.close();
      }

      return pessoas;
   }

   public Pessoa remove( final Integer id ) {
      Pessoa p = null;
      EntityManager em = new ConnectionFactory().getConnection();

      try {
         p = em.find( Pessoa.class, id);

         if (p != null) {
            em.getTransaction().begin();
            em.remove( p );
            em.getTransaction().commit();
         }
      } catch (Exception e) {
         em.getTransaction().rollback();
         System.err.println( e );
      } finally {
         em.close();
      }

      return p;
   }

}
