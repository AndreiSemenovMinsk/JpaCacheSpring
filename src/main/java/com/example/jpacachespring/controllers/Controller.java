package com.example.jpacachespring.controllers;

import com.example.jpacachespring.aop.model.ParentDTO;
import com.example.jpacachespring.aop.model.PersonDTO;
import com.example.jpacachespring.aop.repo.ParentJPARepository;
import com.example.jpacachespring.aop.repo.PersonJPARepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {
/*
    @Autowired
    private PersonJPARepository personJPARepository;
    @Autowired
    private ParentJPARepository parentJPARepository;

    @GetMapping("/save")
    public void save() throws InterruptedException {

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            var p1 = new ParentDTO();
            p1.setName("P" + i);
            p1.setAge(i);
            parentJPARepository.save(p1);

            for (int j = 0; j < 10; j++) {
                var c1 = new PersonDTO();
                c1.setName("C" + j);
                c1.setAge(j);
                //c1.setParentDTOId(p1.getId());
                //personJPARepository.save(c1);
                p1.addPersonList(c1);
            }

            parentJPARepository.save(p1);
        }

        System.out.println("*save time " + (System.currentTimeMillis() - start));
    }

    @GetMapping("/perform1")
    public void performOperation1() throws InterruptedException {
        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
        CountDownLatch countDownLatch = new CountDownLatch(1000);

        long start = System.currentTimeMillis();

        int s = 0;
        for (int i = 0; i < 1000; i++) {
            int finI = i;
            //executor.submit(() -> {
                List<ParentDTO> parentss = parentJPARepository.findByNameAndAge("P" + finI, finI);

                parentss.forEach(e -> {
                    try {
                        //System.out.println("Achild  " + personJPARepository.findByParentDTOId(e.getId()));
                        personJPARepository.findByParentDTOId(e.getId());

                        e.setId(finI);

                        parentJPARepository.save(e);

                        System.out.println("finI " + finI + " e.getId() " + e.getId());

                        //System.out.println("Bchild  " + personJPARepository.findByParentDTOId(e.getId()));
                        var child = personJPARepository.findByParentDTOId(e.getId());

                        personJPARepository.save(child.get(0));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
                //countDownLatch.countDown();
                //return null;
            //});
            s++;
        }
        //countDownLatch.await();
        System.out.println(s);
        System.out.println(System.currentTimeMillis() - start);
    }

    @GetMapping("/perform2")
    public void performOperation2() throws InterruptedException {
        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
        CountDownLatch countDownLatch = new CountDownLatch(1000);

        long start = System.currentTimeMillis();

        int s = 0;
        for (int i = 0; i < 1; i++) {
            int finI = i;
            //executor.submit(() -> {
            List<ParentDTO> parentss = parentJPARepository.findByNameAndAge("P" + finI, finI);
            parentss.forEach(parent -> {
                try {
                    //System.out.println("parent@@@ " + parent.getName());
                    System.out.println("Achild  " +
                            personJPARepository.findByParentDTONameAndAge(parent.getName(), 7));
                    //personJPARepository.findByParentDTONameAndAge(parent.getName(), 7);

                    parent.setName(parent.getName() + "@");

                    parentJPARepository.save(parent);

                    //System.out.println("parent*** " + parent.getName());

                    System.out.println("Bchild  "
                            + personJPARepository.findByParentDTONameAndAge(parent.getName(), 7));
                    //personJPARepository.findByParentDTOId(parent.getId());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            //countDownLatch.countDown();
            //return null;
            //});
            s++;
        }
        //countDownLatch.await();
        System.out.println(s);
        System.out.println(System.currentTimeMillis() - start);
    }



    @GetMapping("/perform3")
    public void performOperation3() throws InterruptedException {

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            var p1 = new ParentDTO();
            p1.setName("P" + i);
            p1.setAge(i);
            parentJPARepository.save(p1);

            for (int j = 0; j < 10; j++) {
                var c1 = new PersonDTO();
                c1.setName("C" + j);
                c1.setAge(j);
                c1.setParentDTOId(p1.getId());
                personJPARepository.save(c1);

            }

            System.out.println(p1.getPersonList().size());
        }

        System.out.println("*save time " + (System.currentTimeMillis() - start));
    }



    @GetMapping("/find1")
    public void find1() throws InterruptedException {
        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
        CountDownLatch countDownLatch = new CountDownLatch(1000);

        long start = System.currentTimeMillis();

        int s = 0;
        for (int i = 0; i < 1000; i++) {
            int finI = i;
            executor.submit(() -> {
            List<ParentDTO> parentss = parentJPARepository.findByNameAndAge("P" + finI, finI);
            parentss.forEach(e -> {
                try {
                    //System.out.println("parent " + e);
                    System.out.println("Achild  " + personJPARepository.findByParentDTOId(e.getId()));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            countDownLatch.countDown();
            return null;
            });
            s++;
        }
        countDownLatch.await();
        System.out.println(s);
        System.out.println(System.currentTimeMillis() - start);
    }

    @GetMapping("/find2")
    public void find() throws InterruptedException {
        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
        CountDownLatch countDownLatch = new CountDownLatch(1000);

        long start = System.currentTimeMillis();

        int s = 0;
        for (int i = 0; i < 1000; i++) {
            int finI = i;
            executor.submit(() -> {
            List<ParentDTO> parentss = parentJPARepository.findByNameAndAge("P" + finI, finI);
            parentss.forEach(parent -> {
                try {
                    System.out.println("Achild  " +
                            personJPARepository.findByParentDTONameAndAge(parent.getName(), 7));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            countDownLatch.countDown();
            return null;
            });
            s++;
        }
        countDownLatch.await();
        System.out.println(s);
        System.out.println(System.currentTimeMillis() - start);
    }

    @GetMapping("/small")
    public void smallOperation() throws InterruptedException {

        System.out.println("@@@@@");
        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
        CountDownLatch countDownLatch = new CountDownLatch(10);
        long start = System.currentTimeMillis();


        for (int i = 0; i < 10; i++) {
            var p1 = new ParentDTO();
            p1.setName("P" + i);
            p1.setAge(i);
            parentJPARepository.save(p1);

            for (int j = 0; j < 10; j++) {
                var c1 = new PersonDTO();
                c1.setName("C" + j);
                c1.setAge(j);
                c1.setParentDTOId(p1.getId());
                personJPARepository.save(c1);
            }
        }

        long store = System.currentTimeMillis() - start;
        start = System.currentTimeMillis();

        int s = 0;
        for (int i = 0; i < 10; i++) {
            int finI = i;
            executor.submit(() -> {

                List<ParentDTO> parentss = parentJPARepository.findByNameAndAge("P" + finI, finI);

                parentss.forEach(e -> {

                    //System.out.println("parent " + e);
                    System.out.println("Achild  " + personJPARepository.findByParentDTOId(e.getId()));

                    e.setId(finI);

                    parentJPARepository.save(e);

                    System.out.println("Bchild  " + personJPARepository.findByParentDTOId(e.getId()));
                });

                countDownLatch.countDown();
                return null;
            });
            s++;
        }
        countDownLatch.await();
        System.out.println(s);

        System.out.println(System.currentTimeMillis() - start);
        System.out.println("store " + store);
    }

    @PostMapping("greeting")
    public void newEmployee(@RequestBody Greeting newEmployee) {
        //myRepository.save(newEmployee);
    }


     */
}
