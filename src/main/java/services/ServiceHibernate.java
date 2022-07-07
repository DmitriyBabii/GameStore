package services;

import intarfaces.Entity;
import intarfaces.EntityRealize;
import models.*;
import models.enums.*;
import models.figures.Client;
import models.figures.Courier;
import models.figures.Manager;
import models.figures.Storekeeper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import services.entity.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ServiceHibernate {
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private static Session session;

    private static final CriterionService criterionService = new CriterionService();

    private ServiceHibernate() {

    }

    public static void open() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    public static void close() {
        session.getTransaction().commit();
        session.close();
    }

    public static void start() {
        EntityRealize[] services = {
                new UserService(),
                new ProductService(),
                new StorageService(),
                new RatingService(),
                new OrderService(),
                new WaitingProductService(),
                new ReservedProductService()
        };
        for (EntityRealize service : services) {
            service.createTable();
        }

        Product[] products = {
                new Product("The Quarry", "https://it.itorrents-igruha.org/uploads/posts/2022-06/1654817418_cover.jpg",
                        Date.valueOf(LocalDate.now()), "The Quarry is a third-person horror game that invites you to go to the open spaces of a large camp, where the leaders decided to have a party and have a good time without children and adults. Usually such parties end up wonderfully, with further love relationships or strong friendships.\n" +
                        "But this time you have to gather your thoughts and take your time with decisions, because terrible hunters will descend on the camp, whose goal is to kill everyone to the last. Therefore, you just have to download The Quarry torrent on your PC and after that you can go on an adventure. Just be prepared for dynamic events\n" +
                        "there will be very little time to make decisions.", AgeLimit._18, 5000.0),
                new Product("V Rising", "https://it.itorrents-igruha.org/uploads/posts/2022-05/1653406426_cover.jpg",
                        Date.valueOf(LocalDate.now()), "V Rising is an exciting survival game set in a fantasy gothic world." +
                        "Build a real empire! Players play the role of a weakened vampire, after a long sleep, whose dream is to create a real empire, which will be feared not only by people living in a gloomy country,\n" +
                        "but also monsters from the deserts and various bloodsuckers. Being a real vampire, we can do absolutely anything we want, but only at night, because during the day it is necessary to hide in the shade, from sunlight. We recommend that you download V Rising torrent for PC and become an all-powerful," +
                        " cold-blooded vampire who hunted people.", AgeLimit._18, 1000.0),
                new Product("ICARUS 2022", "https://it.itorrents-igruha.org/uploads/posts/2021-12/1638577674_8honqi1fw5xjmwkoai2txq.jpeg",
                        Date.valueOf(LocalDate.now()), "ICARUS is a survival simulator that is focused not solely on creating conditions for survival, but on organizing the extraction of a rare and expensive resource on an uninhabited planet. Despite the fact that the planet Icarus has an almost identical climate, like on Earth, you should not relax.\n" +
                        "After all, the atmosphere of the planet has long been poisoned and people cannot exist on it. And in order to somehow change the situation, you first have to download the ICARUS game via torrent, and only then master all the functions and features in this game adventure. Remember that any mistake on this planet can cost you your life.",
                        AgeLimit._18, 500.0),
                new Product("Learning Factory", "https://it.itorrents-igruha.org/uploads/posts/2021-07/1627129619_imgonline-com-ua-resize-mokdngl00nhqm.jpg",
                        Date.valueOf(LocalDate.now()), "It's strategy and simulation in one game. The theme of the game is construction and automation. You will play as a strange but brilliant engineer who is dealing with a very important problem - how to understand cats.\n" +
                        "\n" +
                        "You just need to create the perfect cat. First of all, you need to collect a huge amount of information about millions of cats,\n" +
                        "analyze it and process it with machine learning models. To make a scientific breakthrough, you just need to follow the instructions:\n" +
                        "make repairs to the factory, which is located on Mars;\n" +
                        "\n" +
                        "form production chains and set them up well;\n" +
                        "\n" +
                        "start the production of goods for cats;\n" +
                        "\n" +
                        "collect and analyze data on cats;\n" +
                        "increase the efficiency of the factory;\n" +
                        "\n" +
                        "learn the basics of machine learning and, of course, the desires of cats.", AgeLimit._18, 500.0),
                new Product("Forspoken", "https://it.itorrents-igruha.org/uploads/posts/2022-01/1642619139_cover.jpg",
                        Date.valueOf(LocalDate.now()), "If the world is on the brink of destruction, urgent action must be taken. Someone will stop as a hero, someone will use the help of magical creatures, but our heroine of today's adventure is full of hatred for enemies. They destroyed most of her people and now she lives in revenge and the desire for the total destruction of these creatures. She is confident\n" +
                        "brave and strong, so you can hope that she will succeed. At the same time, the girl has unusual skills to control nature, which will become her main weapon. At the same time, she can deftly move along the rocks, use the element of wind and much more. Her enemies will definitely not be in trouble, as she is ready to destroy everyone and everyone.",
                        AgeLimit._18, 500.0),
                new Product("STALKER 2", "https://it.itorrents-igruha.org/uploads/posts/2021-06/1623695388_cover.jpg",
                        Date.valueOf(LocalDate.now()), "S.T.A.L.K.E.R. 2: Heart of Chernobyl is an exciting action and shooter set in the Chernobyl exclusion zone.\n" +
                        "\n" +
                        "Feel like a real stalker!\n" +
                        "\n" +
                        "Players will play the role of a lone stalker and go on a journey to conduct research in a radioactive zone. On his way,\n" +
                        "you will meet representatives of different factions and will decide which of them is worthy of living. Also, you will fight and participate in various firefights, with different opponents, using unique tactics to deceive you. We invite you to immerse yourself in the gloomy and tense atmosphere of the post-apocalypse and download S.T.A.L.K.E.R.\n" +
                        "2: Heart of Chernobyl torrent free in Russian.", AgeLimit._18, 500.0),
                new Product("Uncharted 4", "https://it.itorrents-igruha.org/uploads/posts/2021-05/1622208177_cover.jpg",
                        Date.valueOf(LocalDate.now()), "Uncharted 4 is an exciting action and adventure computer game that tells us about the price of a treasure. A bright adventure awaits you, in the jungle, snow-capped peaks and in the most beautiful corners of the earth. We advise you to download Uncharted 4 via torrent to your computer for free in Russian and spend your leisure time with interest.\n" +
                        "\n" +
                        "In search of treasure\n" +
                        "Teaming up with Nathan Drake, his brother Sam, wife Elena, and best friend and mentor Sally, players can embark on an epic adventure around the world to find lost treasure.",
                        AgeLimit._18, 500.0),
                new Product("Shadow Warrior 3", "https://it.itorrents-igruha.org/uploads/posts/2022-03/1646147424_library_600x900.jpg",
                        Date.valueOf(LocalDate.now()), "If you like active adventures and at the same time dream of shredding everyone to pieces, we suggest that you pay attention to the opportunity to fight demons and try to get the most out of the game Shadov Warrior 3. From the name of the game, you probably understood that this is a continuation of the cult entertainment.\n" +
                        "This time you will again take on the role of a crazy warrior who is ready to fight the forces of darkness and destroy everyone in his path. It remains only to closely monitor all the events that take place and try to effectively use any available opportunities for self-realization.\n" +
                        "True, now you have to fight with more cruel and dangerous opponents.", AgeLimit._18, 500.0),
                new Product("Saints Row 2022", "https://it.itorrents-igruha.org/uploads/posts/2022-01/1642620143_cover.jpg",
                        Date.valueOf(LocalDate.now()), "Saints Row 2022 is a scandalous and bloody action game that will take place in the fictional city of Santo Illeso, where one group is trying to create a criminal empire.\n" +
                        "\n" +
                        "Become the king of Santo Illeso\n" +
                        "\n" +
                        "The main character will be accompanied by 3 more characters who will support him in his difficult missions.\n" +
                        "You will meet the tactician Elei, the self-confident DJ Kevin and the experienced mechanic and driver Nina, with whom you can build your kingdom. Also, you will take part in exciting battles, with enemy factions flooding the streets and deal with the company of Dos Panteros and Marshall. If you are looking for crazy action movies,\n" +
                        "it's time to download Saints Row 2022 torrent on pc.",
                        AgeLimit._18, 500.0),
                new Product("Ghostwire Tokyo", "https://it.itorrents-igruha.org/uploads/posts/2022-02/1644176907_cover.jpg",
                        Date.valueOf(LocalDate.now()), "If you like horror games, then we have good news for you, now you can easily download Ghostwire Tokyo via torrent in Russian. This time it will be possible to go on an exciting adventure that will take place in the capital of Japan. You will encounter very dark and unusual paranormal phenomena,\n" +
                        "who do everything to kidnap people. They have been doing this for quite some time, as thousands of people have already disappeared according to the latest news reports.",
                        AgeLimit._18, 500.0),
                new Product("Vampire The Masquerade Bloodlines 2", "https://it.itorrents-igruha.org/uploads/posts/2019-03/1553791142__cover.jpg",
                        Date.valueOf(LocalDate.now()), "Fans have been waiting for this for a long time and now, after 15 years, it has finally happened - now the gaming market will soon be conquered by a new and most long-awaited game project called Vampire The Masquerade Bloodlines 2. Finally, users will find out what happened to the town called Seattle, which is the capital of vampire life.\n" +
                        "And to join the public view, it will be enough to download the torrent Vampire The Masquerade Bloodlines 2 after the release. It remains only to wait for this moment and again try on the role of a bloodthirsty vampire.",
                        AgeLimit._18, 500.0),
                new Product("Tiny Tina's Wonderlands", "https://it.itorrents-igruha.org/uploads/posts/2022-02/1644177409_cover.jpg",
                        Date.valueOf(LocalDate.now()), "Tiny Tina's Wonderlands is a fantasy shooter that will be announced in 2022. Get ready for a brand new adventure! Tiny Tina's Wonderlands has been officially announced at the Kickoff Live Summer Game Fest. Ashly Burch joined host Geoff Keighley to talk about her character Tiny Tina, the beloved star\n" +
                        "falling in Wonderland Tiny Tina. In the game, Tina is your guide to an unusual and chaotic fantasy realm. Download Tiny Tina's Wonderlands for free via torrent in Russian on our website you can now!",
                        AgeLimit._18, 500.0),
                new Product("Conan Exiles", "https://it.itorrents-igruha.org/uploads/posts/2022-01/1641516227_cover.jpg",
                        Date.valueOf(LocalDate.now()), "Dare to Stay - in a small town located somewhere in Pennsylvania, something completely strange begins to happen. The evil forces that once haunted an ordinary family are returning again. Well, well, you will play as a rather fearless investigator and together with him you must solve the problem looming over the city!",
                        AgeLimit._18, 500.0),
                new Product("Dare to Stay", "https://it.itorrents-igruha.org/uploads/posts/2022-07/1656683517_dare-to-stay-pc-free-download.jpg",
                        Date.valueOf(LocalDate.now()), "Dare to Stay - in a small town located somewhere in Pennsylvania, something completely strange begins to happen. The evil forces that once haunted an ordinary family are returning again. Well, well, you will play as a rather fearless investigator and together with him you must solve the problem looming over the city!", AgeLimit._18, 500.0),
                new Product("White Shadows", "https://it.itorrents-igruha.org/uploads/posts/2020-09/1599483831_4.jpg",
                        Date.valueOf(LocalDate.now()), "Not always the heroes of interactive adventures are eager to be bright, pleasant and do good. Some of them seek darkness in order to reveal the truth, to become a victim and a criminal. Today's story will touch the dark times,\n" +
                        "when almost all of humanity was wiped off the face of the earth and only small cities were able to provide themselves with constant light. After all, they say that the main element of the plague that killed everyone was darkness. What is in it? Why do people try to avoid shadows? These questions can only be answered if\n" +
                        "if you become one with darkness. But is it worth taking this step? It's up to you to decide, as the hero is ready for this and will definitely not back down.",
                        AgeLimit._18, 500.0),
                new Product("Assetto Corsa Competizione", "https://it.itorrents-igruha.org/uploads/posts/2019-05/1559215977__cover.jpg",
                        Date.valueOf(LocalDate.now()), "The game Assetto Corsa Competizione torrent download which is free to download, is a new official project for the Blancpain GT Series racing tournament.\n" +
                        "With outstanding simulation quality, this game will let you experience the atmosphere of the GT3 championship as you compete against famous drivers and famous teams on recreated tracks.\n" +
                        "All elements in the game are made with the utmost attention to detail, which made it possible to convey the spirit of real competitions. Experience an incredible level of racing realism in modes such as Sprint, Spa 24 Hours and Long Distance Ride in solo and multiplayer modes.",
                        AgeLimit._18, 500.0)
        };


        Storage storage = new Storage(products[0], 10);

        WaitingProduct waitingProduct = new WaitingProduct(products[1], 1);

        ReservedProduct reservedProduct = new ReservedProduct(products[1], 1);

        Client client = new Client("Dima", "Babii", "Mitar", "Babii0706",
                "+380504021867", "v.babiy75@gmail.com", Date.valueOf(LocalDate.now()));
        Rating rating = new Rating(client, products[0], "dadsasd", Date.valueOf(LocalDate.now()));
        Manager manager = new Manager("Manager", "Babii", "Mitar1", "Babii0706",
                "+380504121867", "1.babiy75@gmail.com", Date.valueOf(LocalDate.now()));
        Storekeeper storekeeper = new Storekeeper("Storekeeper", "Babii", "Mitar2", "Babii0706",
                "+380504221867", "2.babiy75@gmail.com", Date.valueOf(LocalDate.now()));

        Courier[] couriers = new Courier[6];
        for (int i = 0; i < couriers.length; i++) {
            couriers[i] = new Courier("Co" + i, "Babii", "co" + i, "Babii0706",
                    "+38050422187" + i, i + "2.babiy75@gmail.com", Date.valueOf(LocalDate.now()));
        }

        Courier courier = new Courier("Courier", "Babii", "Mitar3", "Babii0706",
                "+380504321867", "3.babiy75@gmail.com", Date.valueOf(LocalDate.now()));

        /*List<Product> productList = new ArrayList<>();
        productList.add(products[0]);
        productList.add(products[1]);
        Order order = new Order(manager, storekeeper, courier, client,
                productList, 2200.0, Date.valueOf(LocalDate.now()));
         */

        Order[] orders = new Order[15];
        for (int i = 0; i < orders.length; i++) {
            Random rand = new Random();
            List<Product> productList = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                productList.add(products[rand.nextInt(products.length)]);
            }
            orders[i] = new Order(manager, storekeeper, null, client,
                    productList, Date.valueOf(LocalDate.now()));
        }

        System.out.println(orders[0].getProducts());

        Entity[] entities = {
                client, manager, storekeeper, courier
        };


        ProductService productService = new ProductService();
        StorageService storageService = new StorageService();
        UserService userService = new UserService();
        RatingService ratingService = new RatingService();
        OrderService orderService = new OrderService();
        WaitingProductService waitingProductService = new WaitingProductService();
        ReservedProductService reservedProductService = new ReservedProductService();

        productService.save(products);
        storageService.save(storage);
        userService.save(entities);
        userService.save(couriers);
        ratingService.save(rating);
        orderService.save(orders);
        waitingProductService.save(waitingProduct);
        reservedProductService.save(reservedProduct);

        /*products[0].setPrice(0.1);
        storage.setQuantity(1);
        client.setName("1");
        manager.setName("1");
        storekeeper.setName("1");
        courier.setName("1");
        rating.setReview("1");
        //order.setEndDateStorekeeper(Date.valueOf(LocalDate.now()));
        //order.addProduct(products[2]);
        waitingProduct.setQuantity(2);
        reservedProduct.setQuantity(2);*/

        /*productService.update(products[0]);
        storageService.update(storage);
        userService.update(client);
        userService.update(manager);
        userService.update(storekeeper);
        userService.update(courier);
        ratingService.update(rating);
        //orderService.update(order);
        waitingProductService.update(waitingProduct);
        reservedProductService.update(reservedProduct);*/


        /*CriterionService criterionProduct = new CriterionService();
        criterionProduct.addCriterion(EProduct.name, "Witcher 3");
        criterionProduct.addCriterion(EProduct.price, Operator.NOT_EQUALS, 2.2);
        System.out.println(productService.select(criterionProduct.getCriterionList()));

        CriterionService criterionStorage = new CriterionService();
        criterionStorage.addCriterion(EStorage.quantity, 1);
        System.out.println(storageService.select(criterionStorage.getCriterionList()));

        CriterionService criterionUser = new CriterionService();
        criterionUser.addCriterion(EUser.last_name, "Babii");
        System.out.println(userService.select(criterionUser.getCriterionList()));

        CriterionService criterionRating = new CriterionService();
        criterionRating.addCriterion(ERating.review, "1");
        System.out.println(ratingService.select(criterionRating.getCriterionList()));

        CriterionService criterionOrder = new CriterionService();
        criterionOrder.addCriterion(EOrder.price, 2200);
        criterionOrder.addCriterion(EOrder.end_date_storekeeper, Operator.NOT_NULL, null);
        System.out.println(orderService.select(criterionOrder.getCriterionList()));

        CriterionService criterionWaiting = new CriterionService();
        criterionWaiting.addCriterion(EWaitingProduct.quantity, 2);
        System.out.println(waitingProductService.select(criterionWaiting.getCriterionList()));

        CriterionService criterionReserved = new CriterionService();
        criterionReserved.addCriterion(EReservedProduct.quantity, 2);
        System.out.println(reservedProductService.select(criterionReserved.getCriterionList()));*/

        /*storageService.delete(storage);
        orderService.delete(order);
        ratingService.delete(rating);
        userService.delete(entities);
        waitingProductService.delete(waitingProduct);
        reservedProductService.delete(reservedProduct);
        productService.delete(product, product1, product2);*/
    }

    public static Session getSession() {
        return session;
    }

    public static void setSearchCriterion(String name) {
        criterionService.clear();
        if (name != null) {
            criterionService.addCriterion(EProduct.name, Operator.LIKE, "%" + name + "%");
        }
    }

    public static List<Criterion> getProductCriterion(String id) {
        criterionService.clear();
        if (id != null) {
            criterionService.addCriterion(EProduct.id_product, Operator.EQUALS, id);
        }
        return criterionService.getCriterionList();
    }

    public static List<Criterion> getReviewCriterion(String id) {
        criterionService.clear();
        if (id != null) {
            criterionService.addCriterion(ERating.id_product_fk, Operator.EQUALS, id);
        }
        return criterionService.getCriterionList();
    }

    public static List<Criterion> getUserLoginCriterion(String username, String password) {
        criterionService.clear();
        if (username != null && password != null) {
            criterionService.addCriterion(EUser.username, Operator.EQUALS, username);
            criterionService.addCriterion(EUser.password, Operator.EQUALS, password);
        }
        return criterionService.getCriterionList();
    }

    public static List<Criterion> getCriterion() {
        return criterionService.getCriterionList();
    }
}
