package org.ukiuni.jpaInspect;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.ukiuni.jpaInspect.entity.Person;

public class CriteriaMain {
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("myUnitInPersistenceXML");
		EntityManager em = factory.createEntityManager();

		{
			System.out.println("insert start");
			EntityTransaction entityTransaction = em.getTransaction();
			entityTransaction.begin();
			Person person = new Person();
			person.setName("niceGuy");
			person.setAge(60);
			person.setCreatedAt(new Date());
			Person betterHalf = new Person();
			betterHalf.setName("cute");
			betterHalf.setAge(67);
			betterHalf.setPartner(betterHalf);
			betterHalf.setCreatedAt(new Date());

			person.setPartner(betterHalf);
			betterHalf.setPartner(person);

			em.persist(person);
			entityTransaction.commit();
			System.out.println("insert end generated id = " + person.getId() + ": and other id = " + betterHalf.getId());
		}
		System.out.println("query item start");

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Person> query = cb.createQuery(Person.class);
		Root<Person> r = query.from(Person.class);

		query.where(cb.and(cb.ge(r.get("age").as(Long.class), 30), cb.like(r.get("name").as(String.class), "%nice%"), cb.isNull(r.get("deletedAt")), cb.or(cb.isNotNull(r.get("partner")))));
		List<Person> persons = em.createQuery(query.select(r)).getResultList();
		System.out.println("selected persons length = " + persons.size());
		System.out.println("pertner name = " + persons.get(0).getPartner().getName());

		em.close();
	}
}
