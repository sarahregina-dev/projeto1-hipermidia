package org.example.controller;

import org.example.model.Inventory;
import org.example.model.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryControllerTest {

    @Test
    public void pickUp_quando_tem_espaco_retorna_msg_de_sucesso_e_item_vai_para_inventario() {
        Inventory inv = new Inventory(2);
        InventoryController ic = new InventoryController(inv);

        Item chave = new Item("Chave de Ferro", "Uma chave pesada e antiga");

        String msg = ic.pickUp(chave);

        assertEquals("Você pegou o item: Chave de Ferro", msg);
        assertEquals(1, inv.getItems().size(), "Inventário deveria ter 1 item depois de pickUp");
        assertSame(chave, inv.getItems().get(0), "O item adicionado deveria ser exatamente a mesma instância");
    }

    @Test
    public void pickUp_quando_inventario_cheio_retorna_msg_de_erro_e_nao_adiciona() {
        Inventory inv = new Inventory(1);
        InventoryController ic = new InventoryController(inv);

        Item chave = new Item("Chave", "Uma chave qualquer");
        Item pocao = new Item("Poção", "Líquido brilhante");

        ic.pickUp(chave); // encheu

        String msg = ic.pickUp(pocao);

        assertEquals("Inventário cheio! Não é possível pegar o item: Poção", msg);
        assertEquals(1, inv.getItems().size(), "Inventário deve continuar com 1 item só");
        assertEquals("Chave", inv.getItems().get(0).getItemName(), "O item antigo deve continuar lá");
    }

    @Test
    public void drop_quando_item_existe_remove_e_retorna_msg_ok() {
        Inventory inv = new Inventory(5);
        InventoryController ic = new InventoryController(inv);

        Item lanterna = new Item("Lanterna", "Ajuda a enxergar no escuro");

        // primeiro pega
        ic.pickUp(lanterna);

        // agora drop
        String msg = ic.drop(lanterna);

        assertEquals("Você largou o item: Lanterna", msg);
        assertTrue(inv.getItems().isEmpty(), "Inventário deveria voltar a ficar vazio");
    }

    @Test
    public void drop_quando_item_nao_existe_retorna_msg_de_erro_e_nada_muda() {
        Inventory inv = new Inventory(5);
        InventoryController ic = new InventoryController(inv);

        Item faca = new Item("Faca", "Lâmina afiada");

        String msg = ic.drop(faca);

        assertEquals("Item não encontrado no inventário: Faca", msg);
        assertTrue(inv.getItems().isEmpty(), "Inventário deveria continuar vazio");
    }

    @Test
    public void listItems_quando_vazio_retorna_mensagem_de_vazio() {
        Inventory inv = new Inventory(3);
        InventoryController ic = new InventoryController(inv);

        String result = ic.listItems();

        assertEquals("Seu inventário está vazio.", result);
    }

    @Test
    public void listItems_quando_tem_itens_lista_todos_em_linhas_separadas() {
        Inventory inv = new Inventory(5);
        InventoryController ic = new InventoryController(inv);

        ic.pickUp(new Item("Chave", "Chave velha"));
        ic.pickUp(new Item("Lanterna", "Lanterna fraca"));

        String result = ic.listItems();

        // esperado:
        // Itens no inventário:
        // - Chave
        // - Lanterna

        assertTrue(result.startsWith("Itens no inventário:\n"));
        assertTrue(result.contains("- Chave\n"));
        assertTrue(result.contains("- Lanterna\n"));
    }

    @Test
    public void hasItemByName_retorna_true_quando_item_esta_no_inventario() {
        Inventory inv = new Inventory(2);
        InventoryController ic = new InventoryController(inv);

        ic.pickUp(new Item("Chave Dourada", "Abre a porta do templo"));

        assertTrue(ic.hasItemByName("chave dourada"));
        assertTrue(ic.hasItemByName("Chave Dourada"));
        assertFalse(ic.hasItemByName("Poção Misteriosa"));
    }
}
