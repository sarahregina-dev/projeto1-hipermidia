package org.example.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {

    @Test
    public void cria_item_com_dados_corretos() {
        Item item = new Item(
                "chave",
                "Uma chave enferrujada e pesada"

        );

        assertEquals("chave", item.getItemName());
        assertEquals("Uma chave enferrujada e pesada", item.getDescription());
        assertEquals("key", item.getType());
    }

    @Test
    public void itens_iguais_devem_ser_considerados_iguais() {
        Item a = new Item("poção", "Restaura vida");
        Item b = new Item("poção", "Restaura vida");

        // isso só vai passar se você tiver sobrescrito equals/hashCode
        assertEquals(a, b, "Dois itens idênticos deveriam ser iguais");
        assertEquals(a.hashCode(), b.hashCode(), "hashCode também deveria bater");
    }

    @Test
    public void toString_deve_conter_nome_tipo_e_desc() {
        Item espada = new Item("Espada Curta", "Uma lâmina simples.");


    }
}