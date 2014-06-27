package org.ukiuni.jpaInspect.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Cart {
	@Id
	@GeneratedValue
	private long id;
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
	private List<Item> items = new ArrayList<Item>();
	private String key;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void addItem(Item item) {
		items.add(item);
		item.setCart(this);
	}
}
