package com.example.jpacachespring.aop;

import static org.springframework.util.CollectionUtils.isEmpty;

import com.example.jpacachespring.aop.model.DTO;
import com.example.jpacachespring.aop.mappers.PersonEntityMapper;
import com.example.jpacachespring.aop.repo.JpaRepositoryTest;
import jakarta.annotation.PostConstruct;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CacheAspect {

    Map<String, Integer> repos = new HashMap<>();

    List<ConcurrentHashMap<Integer, DTO>> idMap = new ArrayList<>();
    //порядок из parameters
    List<List<ConcurrentHashMap<String, Object>>> mapRepos = new ArrayList<>();

    List<Map<Integer, List<String>>> dtoKeys = new ArrayList<>();
    List<List<Map<Integer, Set<Integer>>>> mapDependant = new ArrayList<>();

    //List<List<ConcurrentHashMap<String, Object>>> mapFields = new ArrayList<>();
    List<List<String[]>> parameters = new ArrayList<>();
    List<List<Integer[]>> parameterDTOFieldInds = new ArrayList<>();
    //порядковый номер метода по названию
    List<Map<String, Integer>> methods = new ArrayList<>();

    List<List<Integer>> collectionType = new ArrayList<>();


    List<String> dtoNames = new ArrayList<>();
    List<String[]> dtoFieldNames = new ArrayList<>();
    List<String[]> dtoFieldCollectionNames = new ArrayList<>();
    List<Integer[]> dtoFieldCollectionOrder = new ArrayList<>();
    List<Map<String, Integer>> dtoFieldsMap = new ArrayList<>();
    //List<List<Integer>> dtoFieldNames = new ArrayList<>();

    List<List<Integer>> parentDTOChildDTOIndex = new ArrayList<>();
    List<List<Integer>> parentDTOChildDTOFieldIndex = new ArrayList<>();
    List<List<List<Integer>>> parentDTOChildMethodIndex = new ArrayList<>();
    List<List<List<Integer>>> parentDTOChildParamIndex = new ArrayList<>();

    List<List<Integer>> childDTOFieldParentDTOIndex = new ArrayList<>();
    List<List<Integer>> childDTOFieldParentFieldDTOIndex = new ArrayList<>();
    List<List<Integer>> childDTOFieldParentFieldIndex = new ArrayList<>();

    List<List<List<Integer>>> DTOFieldParamIndex = new ArrayList<>();
    List<List<List<Integer>>> DTOFieldMethodParamDTOFieldIndex = new ArrayList<>();
    List<List<List<Integer>>> DTOFieldMethodIndex = new ArrayList<>();


    List<List<List<Integer>>> childDTOMethodParentDTOList = new ArrayList<>();
    List<List<List<Integer>>> childDTOMethodParentMethodRequestList = new ArrayList<>();
    List<List<List<Integer>>> childDTOMethodParentFieldDTOList = new ArrayList<>();

    List<List<List<Integer>>> childDTOMethodParentMethodMethodList = new ArrayList<>();
    List<List<List<List<Integer>>>> childDTOMethodParentMethodRequestParamsList = new ArrayList<>();
    List<List<List<Integer>>> childDTOMethodParentMethodDTOList = new ArrayList<>();
    List<List<Integer[]>> DTOParamFieldIndex = new ArrayList<>();

    //надо придумать использование
    List<List<List<Integer>>> DTOParamFreeFieldIndex = new ArrayList<>();
    //
    List<List<Integer>> DTOMethodDependentToIndependent = new ArrayList<>();


    @Autowired
    ConfigurableApplicationContext context;

    @Autowired
    PersonEntityMapper personEntityMapper;

    @PostConstruct
    public void init() {
        Reflections reflections = new Reflections("com.example.jpacachespring.aop.repo");
        Set<Class<? extends JpaRepositoryTest>> subTypes = reflections
                .getSubTypesOf(JpaRepositoryTest.class);

        Field[][] declaredFieldsDTO = new Field[subTypes.size()][];

        int repoIndex = 0;
        for (var subtype : subTypes) {

            repos.put(subtype.getSimpleName(), repoIndex);
            mapRepos.add(new ArrayList<>());
            idMap.add(new ConcurrentHashMap<>(1024));
            parameters.add(new ArrayList<>());
            parameterDTOFieldInds.add(new ArrayList<>());
            methods.add(new HashMap<>());
            dtoFieldsMap.add(new HashMap<>());
            collectionType.add(new ArrayList<>());
            dtoKeys.add(new HashMap<>());

            List<List<Integer>> parentDTOFieldChildDTOMethod = new ArrayList<>();
            List<List<Integer>> parentDTOFieldChildDTOParam = new ArrayList<>();
            var ee = new ArrayList<Map<Integer, Set<Integer>>>();
            for (int eeI = 0; eeI < subTypes.size(); eeI++) {
                ee.add(new HashMap<>(256));
                parentDTOFieldChildDTOMethod.add(new ArrayList<>());
                parentDTOFieldChildDTOParam.add(new ArrayList<>());
            }
            mapDependant.add(ee);

            parentDTOChildMethodIndex.add(parentDTOFieldChildDTOMethod);
            parentDTOChildParamIndex.add(parentDTOFieldChildDTOParam);

            List<Integer> parentDTOFieldChildDTO = new ArrayList<>();
            List<Integer> parentDTOFieldChildField = new ArrayList<>();
            List<Integer> childDTOFieldParentDTO = new ArrayList<>();
            List<Integer> childDTOFieldParentFieldDTO = new ArrayList<>();
            List<Integer> childDTOFieldParentField = new ArrayList<>();

            Field[] declaredFields = null;

            //сначала анализируем dto
            for (var method : subtype.getDeclaredMethods()) {
                var findMethodName = method.getName();
                if (findMethodName.equals("save") && method.getParameterCount() == 1) {
                    declaredFields = method.getReturnType().getDeclaredFields();

                    declaredFieldsDTO[repoIndex] = declaredFields;

                    dtoNames.add(firstLower(method.getReturnType().getSimpleName()));

                    List<String> a = new ArrayList<>();

                    dtoFieldsMap.get(repoIndex).put("id", 0);
                    childDTOFieldParentDTO.add(null);
                    childDTOFieldParentFieldDTO.add(null);
                    childDTOFieldParentField.add(null);

                    int i = 0;
                    for (int declaredFieldInd = 0; declaredFieldInd < declaredFields.length; declaredFieldInd++) {

                        //if (!"List".equals(declaredFields[declaredFieldInd].getType().getSimpleName())) {

                            var dtoFieldName = declaredFields[declaredFieldInd].getName();
                            a.add(dtoFieldName);

                            dtoFieldsMap.get(repoIndex).put(dtoFieldName, i);

                            childDTOFieldParentDTO.add(null);
                            childDTOFieldParentFieldDTO.add(null);
                            childDTOFieldParentField.add(null);
                            i++;
                        //}
                    }

                    //repo - dto
                    //dtoFieldNames.add(a);
                    dtoFieldNames.add(a.toArray(new String[0]));
                }
            }
            parentDTOChildDTOIndex.add(parentDTOFieldChildDTO);
            parentDTOChildDTOFieldIndex.add(parentDTOFieldChildField);

            childDTOFieldParentDTOIndex.add(childDTOFieldParentDTO);
            childDTOFieldParentFieldDTOIndex.add(childDTOFieldParentDTO);
            childDTOFieldParentFieldIndex.add(childDTOFieldParentDTO);

            //для id
            mapRepos.get(repoIndex).add(new ConcurrentHashMap<>(1024));

            repoIndex++;
        }
        //processParentInit();


        repoIndex = 0;
        for (var subtype : subTypes) {

            DTOParamFieldIndex.add(new ArrayList<>());
            DTOParamFreeFieldIndex.add(new ArrayList<>());
            DTOMethodDependentToIndependent.add(new ArrayList<>());

            childDTOMethodParentDTOList.add(new ArrayList<>());
            childDTOMethodParentFieldDTOList.add(new ArrayList<>());
            childDTOMethodParentMethodRequestList.add(new ArrayList<>());

            childDTOMethodParentMethodMethodList.add(new ArrayList<>());

            childDTOMethodParentMethodRequestParamsList.add(new ArrayList<>());

            childDTOMethodParentMethodDTOList.add(new ArrayList<>());

            var declaredFields = declaredFieldsDTO[repoIndex];

            //теперь анализируем репозитории и методы поиска
            int methodInd = 0;
            for (var method : subtype.getDeclaredMethods()) {
                var findMethodName = method.getName();

                if (findMethodName.startsWith("findBy")) {

                    System.out.println(repoIndex + "repoIndex ++ " + findMethodName+ " @@@methodInd " + methodInd);

                    childDTOMethodParentDTOList.get(repoIndex).add(new ArrayList<>());
                    childDTOMethodParentFieldDTOList.get(repoIndex).add(new ArrayList<>());
                    childDTOMethodParentMethodRequestList.get(repoIndex).add(new ArrayList<>());

                    childDTOMethodParentMethodMethodList.get(repoIndex).add(new ArrayList<>());
                    childDTOMethodParentMethodRequestParamsList.get(repoIndex).add(new ArrayList<>());
                    childDTOMethodParentMethodDTOList.get(repoIndex).add(new ArrayList<>());

                    String[] fields = findMethodName.substring(6).split("And");
                    String[] fieldsLower = new String[fields.length];
                    Integer[] paramFieldIndex = new Integer[fields.length];
                    List<Integer> paramFieldIndexDependantFree = new ArrayList<>();

                    boolean dependsOnParentField = false;
                    for (int fieldIndexFromDto = 0; fieldIndexFromDto < fields.length; fieldIndexFromDto++) {

                        String fieldName = firstLower(fields[fieldIndexFromDto]);

                        if (fieldName.equals("list")) {


                            //continue;
                        }

                        System.out.println("repoIndex "+repoIndex + " fieldName " + fieldName
                                +" "+dtoFieldsMap.get(repoIndex).get(fieldName));

                        paramFieldIndex[fieldIndexFromDto] = dtoFieldsMap.get(repoIndex).get(fieldName);

                        //ищем зависимости по полям
                        if (paramFieldIndex[fieldIndexFromDto] == null) {
                            dependsOnParentField = true;
                            //ищем подходящий родительский класс
                            for (int parentDTOInd = 0; parentDTOInd < dtoNames.size(); parentDTOInd++) {
                                var dtoName = dtoNames.get(parentDTOInd);

                                System.out.println("fieldName " + fieldName + "dtoName" +dtoName + fieldName.startsWith(dtoName));

                                if (fieldName.startsWith(dtoName)) {
                                    //ищем поле в родительском классе
                                    var childFieldNameRest = fieldName.substring(dtoName.length());
                                    var parentField = dtoFieldsMap.get(parentDTOInd).get(firstLower(childFieldNameRest));

                                    System.out.println("251repoIndex"+repoIndex+" methodInd "+methodInd+" parentDTOInd "+parentDTOInd);

                                    childDTOMethodParentDTOList.get(repoIndex).get(methodInd).add(parentDTOInd);
                                    childDTOMethodParentFieldDTOList.get(repoIndex).get(methodInd).add(parentField);

                                    childDTOMethodParentMethodRequestList.get(repoIndex).get(methodInd).add(fieldIndexFromDto);
                                    break;
                                }
                            }
                        } else {
                            fieldsLower[fieldIndexFromDto] = fieldName;
                        }
                    }

                    parameters.get(repoIndex).add(fieldsLower);


                    System.out.println("268 parentInd " + repoIndex + " parentFieldsArr " + Arrays.toString(
                            paramFieldIndex));

                    parameterDTOFieldInds.get(repoIndex).add(paramFieldIndex);

                    mapRepos.get(repoIndex).add(new ConcurrentHashMap<>());
                    //сетим порядковый номер метода по названию
                    methods.get(repoIndex).put(findMethodName, methodInd);

                    if (!dependsOnParentField) {
                        DTOParamFreeFieldIndex.get(repoIndex).add(Arrays.asList(paramFieldIndex));
                    } else {
                        List<Integer> cdc = new ArrayList<>();
                        for (Integer r : paramFieldIndex) {
                            if (r != null) {
                                cdc.add(r);
                            }
                        }

                        DTOParamFreeFieldIndex.get(repoIndex).add(cdc);
                    }
                    DTOMethodDependentToIndependent.get(repoIndex).add(null);
                    DTOParamFieldIndex.get(repoIndex).add(paramFieldIndex);


                    if (method.getReturnType().isAssignableFrom(List.class)) {
                        collectionType.get(repoIndex).add(1);
                    } else if (method.getReturnType().isAssignableFrom(Set.class)) {
                        collectionType.get(repoIndex).add(2);
                    } else {
                        collectionType.get(repoIndex).add(0);
                    }
                    methodInd++;
                }
            }

            var parameter = parameters.get(repoIndex);
            List<List<Integer>> parentFieldParamIndex = new ArrayList<>();
            List<List<Integer>> parentFieldFindMethodIndex = new ArrayList<>();

            List<String> b = new ArrayList<>();
            List<Integer> order = new ArrayList<>();

            for (int declaredFieldInd = 0; declaredFieldInd < declaredFields.length; declaredFieldInd++) {

                System.out.println(repoIndex + " repoIndex declaredFieldInd " + declaredFieldInd + " *** "
                        + declaredFields[declaredFieldInd].getType().getSimpleName());

                if ("List".equals(declaredFields[declaredFieldInd].getType().getSimpleName())) {

                    Type genericFieldType = declaredFields[declaredFieldInd].getGenericType();

                    System.out.println((genericFieldType instanceof ParameterizedType) + " " + genericFieldType.getTypeName());

                    if (genericFieldType instanceof ParameterizedType) {
                        ParameterizedType aType = (ParameterizedType) genericFieldType;

                        final String typeName = aType.getActualTypeArguments()[0].getTypeName();

                        System.out.println(typeName.lastIndexOf("."));

                        final String simpleName = firstLower(typeName.substring(typeName.lastIndexOf(".") + 1));

                        System.out.println(simpleName + " simpleName " + dtoNames.indexOf(simpleName) +" "+ dtoNames);

                        final int index = dtoNames.indexOf(simpleName);
                        if (index > -1) {
                            order.add(index);
                            b.add(declaredFields[declaredFieldInd].getName());
                        }
                    }
                    System.out.println("dtoFieldCollectionNames+++  " + b);

                }

                var dtoFieldName = declaredFields[declaredFieldInd].getName();

                List<Integer> paramIndex = new ArrayList<>();
                List<Integer> findMethodIndex = new ArrayList<>();
                for (methodInd = 0; methodInd < parameter.size(); methodInd++) {

                    for (int parInd = 0; parInd < parameter.get(methodInd).length; parInd++) {

                        if (parameter.get(methodInd)[parInd] != null
                                && parameter.get(methodInd)[parInd].equals(dtoFieldName)) {
                            paramIndex.add(parInd);
                            findMethodIndex.add(methodInd);
                            break;
                        }
                    }
                }
                parentFieldParamIndex.add(paramIndex);
                parentFieldFindMethodIndex.add(findMethodIndex);
            }

            System.out.println(dtoFieldCollectionNames.size() + " b " + b);

            dtoFieldCollectionNames.add(b.toArray(new String[0]));
            dtoFieldCollectionOrder.add(order.toArray(new Integer[0]));


            // совместное использование
            DTOFieldParamIndex.add(parentFieldParamIndex);
            DTOFieldMethodIndex.add(parentFieldFindMethodIndex);

            repoIndex++;
        }

        //проходим по dto - методам и всем зависимым по полям
        for (int childInd = 0; childInd < childDTOMethodParentDTOList.size(); childInd++) {
            for (int childMethodInd = 0; childMethodInd < childDTOMethodParentDTOList.get(childInd).size(); childMethodInd++) {

                //уровень метода в репозитории - список родительских dto
                var parentDTOInd = childDTOMethodParentDTOList.get(childInd).get(childMethodInd);
                //уровень метода в репозитории - список родительских филдов - парно с dto
                var parentDTOFieldInd = childDTOMethodParentFieldDTOList.get(childInd).get(childMethodInd);

                var parentDTORequestParamInd = childDTOMethodParentMethodRequestList.get(childInd).get(childMethodInd);

                if (!isEmpty(parentDTOInd)) {
                    List<Integer> parentDTOList = new ArrayList<>();
                    List<List<Integer>> parentDTOFieldList = new ArrayList<>();
                    List<List<Integer>> parentDTORequestParamList = new ArrayList<>();

                    //формирование структуры parent [parent-dto][parent-field]
                    for (int k = 0; k < parentDTOInd.size(); k++) {
                        int parentInd = parentDTOInd.get(k);
                        int parentFieldInd = parentDTOFieldInd.get(k);
                        int parentRequestInd = parentDTORequestParamInd.get(k);

                        var indexInList = parentDTOList.indexOf(parentDTOInd.get(k));
                        if (indexInList == -1) {
                            indexInList = parentDTOList.size();
                            parentDTOList.add(parentInd);
                            parentDTOFieldList.add(new ArrayList<>());
                            parentDTORequestParamList.add(new ArrayList<>());
                        }
                        parentDTOFieldList.get(indexInList).add(parentFieldInd);
                        parentDTORequestParamList.get(indexInList).add(parentRequestInd);
                    }

                    var childFields = new ArrayList<Integer>();
                    var paramFieldIndex = DTOParamFieldIndex.get(childInd).get(childMethodInd);
                    for (int f = 0; f < paramFieldIndex.length; f++) {
                        if (paramFieldIndex[f] != null) {
                            childFields.add(paramFieldIndex[f]);
                        }
                    }

                    boolean noSuitableChildMethod = true;
                    for (int m = 0; m < parameterDTOFieldInds.get(childInd).size(); m++) {
                        Integer[] params = parameterDTOFieldInds.get(childInd).get(m);
                        if (params.length == childFields.size()) {
                            boolean cont = true;
                            for (var param : params) {
                                if (!childFields.contains(param)) {
                                    cont = false;
                                    break;
                                }
                            }
                            //если нашли подходящий метод в родительском
                            if (cont) {
                                DTOMethodDependentToIndependent.get(childInd).set(childMethodInd, m);

                                noSuitableChildMethod = false;
                                break;
                            }
                        }
                    }

                    if (noSuitableChildMethod) {

                        Integer[] childFieldsArr = new Integer[childFields.size()];
                        String[] fieldNames = new String[childFields.size()];

                        for (int u = 0; u < childFields.size(); u++) {
                            var childField = childFields.get(u);

                            childFieldsArr[u] = childField;

                            var fieldName = dtoFieldNames.get(childInd)[childField];
                            fieldNames[u] = fieldName;
                        }

                        int newMethodIndex = parameters.get(childInd).size();


                        System.out.println("450 childInd " + childInd + " parentFieldsArr " + Arrays.toString(
                                childFieldsArr));

                        parameterDTOFieldInds.get(childInd).add(childFieldsArr);
                        parameters.get(childInd).add(fieldNames);
                        mapRepos.get(childInd).add(new ConcurrentHashMap<>());
                        collectionType.get(childInd).add(1);

                        DTOParamFreeFieldIndex.get(childInd).add(Arrays.asList(childFieldsArr));

                        DTOMethodDependentToIndependent.get(childInd).set(childMethodInd, newMethodIndex);

                        StringJoiner childJoiner = new StringJoiner("And");
                        for (var requestParam : childFieldsArr) {
                            childJoiner.add(requestParam.toString());
                        }
                        var findMethodName = "findBy" + childJoiner.toString();
                        methods.get(childInd).put(findMethodName, newMethodIndex);
                    }


                    System.out.println("@parentDTOList " + parentDTOList);
                    System.out.println("parameterDTOFieldInds + " + parameterDTOFieldInds);

                    //перебор всех родительских репозиториев для одного дочернего метода
                    for (int k = 0; k < parentDTOList.size(); k++) {
                        //поиск подходящего метода в родительском репозитории
                        int parentInd = parentDTOList.get(k);
                        List<Integer> parentFields = parentDTOFieldList.get(k);
                        List<Integer> parentRequests = parentDTORequestParamList.get(k);

                        boolean noSuitableParentMethod = true;
                        for (int m = 0; m < parameterDTOFieldInds.get(parentInd).size(); m++) {
                            Integer[] params = parameterDTOFieldInds.get(parentInd).get(m);
                            if (params.length == parentFields.size()) {
                                boolean cont = true;
                                for (var param : params) {
                                    if (!parentFields.contains(param)) {
                                        cont = false;
                                        break;
                                    }
                                }
                                //если нашли подходящий метод в родительском
                                if (cont) {
                                    childDTOMethodParentMethodDTOList.get(childInd).get(childMethodInd)
                                            .add(parentInd);

                                    System.out.println("@@@childInd "+childInd+" childMethodInd "+childMethodInd+" parentInd "+parentInd);

                                    childDTOMethodParentMethodMethodList.get(childInd).get(childMethodInd)
                                            .add(m);

                                    childDTOMethodParentMethodRequestParamsList.get(childInd).get(childMethodInd)
                                            .add(parentRequests);

                                    noSuitableParentMethod = false;
                                }
                            }
                        }

                        if (noSuitableParentMethod) {
                            Integer[] parentFieldsArr = new Integer[parentFields.size()];
                            String[] fieldNames = new String[parentFields.size()];

                            for (int u = 0; u < parentFields.size(); u++) {
                                var parentField = parentFields.get(u);

                                parentFieldsArr[u] = parentField;

                                var fieldName = dtoFieldNames.get(parentInd)[parentField];
                                fieldNames[u] = fieldName;
                            }

                            System.out.println("519 parentInd " + parentInd + " parentFieldsArr " + Arrays.toString(
                                    parentFieldsArr));

                            parameterDTOFieldInds.get(parentInd).add(parentFieldsArr);
                            parameters.get(parentInd).add(fieldNames);
                            mapRepos.get(parentInd).add(new ConcurrentHashMap<>());
                            collectionType.get(parentInd).add(1);

                            DTOParamFreeFieldIndex.get(parentInd).add(Arrays.asList(parentFieldsArr));


                            StringJoiner childJoiner = new StringJoiner("And");
                            for (var requestParam : parentFieldsArr) {
                                childJoiner.add(requestParam.toString());
                            }
                            var findMethodName = "findBy" + childJoiner.toString();
                            methods.get(parentInd).put(findMethodName, parameters.get(childInd).size());


                            childDTOMethodParentMethodDTOList.get(childInd).get(childMethodInd)
                                    .add(parentInd);

                            System.out.println("***childInd "+childInd+" childMethodInd "+childMethodInd+" parentInd "+parentInd);


                            childDTOMethodParentMethodMethodList.get(childInd).get(childMethodInd)
                                    .add(parameterDTOFieldInds.get(parentInd).size() - 1);

                            childDTOMethodParentMethodRequestParamsList.get(childInd).get(childMethodInd)
                                    .add(parentRequests);
                        }
                    }
                }
            }
        }
        processParentDTO();
    }

    //@Around("@annotation(CacheRequest)")
    @Around("execution(* com.example.jpacachespring.aop.repo.*.findBy*(..))")
    public Object processFind(ProceedingJoinPoint joinPoint) throws Throwable {

        var repoFile = joinPoint.getThis().getClass().getSimpleName();
        var repoIndex = repos.get(repoFile.substring(0, repoFile.length() - 20));
        var methodName = joinPoint.getSignature().getName();
        var args = joinPoint.getArgs();
        int methodOrder = 0;

        methodOrder = methods.get(repoIndex).get(methodName);

        var parentDTOs = childDTOMethodParentMethodDTOList.get(repoIndex).get(methodOrder);


        System.out.println("@repoIndex " + repoIndex + " methodName "+methodName+ " methodOrder"+ methodOrder + " parentDTOs " + parentDTOs);


        if (parentDTOs.size() > 0) {

            List<Integer> idss = new ArrayList<>();

            for (int i = 0; i < parentDTOs.size(); i++) {

                var parentRepoInd = parentDTOs.get(i);
                var parentMethodInd = childDTOMethodParentMethodMethodList.get(repoIndex)
                        .get(methodOrder).get(i);

                var requestParams = childDTOMethodParentMethodRequestParamsList.get(repoIndex)
                        .get(methodOrder).get(i);

                StringJoiner parentJoiner = new StringJoiner("_");
                for (var requestParam : requestParams) {
                    parentJoiner.add(args[requestParam].toString());
                }
                var parentKey = parentJoiner.toString();

                var parentResult = mapRepos.get(parentRepoInd).get(parentMethodInd).get(parentKey);

                int type = collectionType.get(parentRepoInd).get(parentMethodInd);

                Set<Integer> childIds = null;

                if (type == 0) {
                    var parentId = ((DTO) parentResult).getId();
                    childIds = mapDependant.get(parentRepoInd).get(repoIndex).get(parentId);

                    if (i == 0) {
                        idss.addAll(childIds);
                    }
                } else {
                    childIds = ((HashMap<Integer, DTO>) parentResult).values().stream()
                            .map(DTO::getId)
                            .map(parentId -> mapDependant
                                    .get(parentRepoInd)
                                    .get(repoIndex)
                                    .get(parentId))
                            .flatMap(Collection::stream)
                            .collect(Collectors.toSet());
                }

                if (childIds != null) {
                    if (i == 0) {
                        idss.addAll(childIds);
                    } else {
                        idss.retainAll(childIds);
                    }
                }
            }

            int methodIndex = DTOMethodDependentToIndependent.get(repoIndex).get(methodOrder);
            List<Integer> childParamIndexList = null;

            childParamIndexList = DTOParamFreeFieldIndex.get(repoIndex).get(methodIndex);

            StringJoiner joiner = new StringJoiner("_");
            for (Integer i : childParamIndexList) {
                joiner.add(args[i].toString());
            }
            var childKey = joiner.toString();

            Object childObject = mapRepos.get(repoIndex).get(methodIndex).get(childKey);

            int type = collectionType.get(repoIndex).get(methodOrder);
            if (type == 0) {
                if (idss.contains(((DTO) childObject).getId())) {
                    return childObject;
                }
            } else if (type == 1) {
//                Collection<DTO> values = ((HashMap<Integer, DTO>) childObject).values();
//                int valueSize = values.size();
//                int idssSize = idss.size();

                return ((HashMap<Integer, DTO>) childObject).values().stream()
                        .filter(e -> idss.contains(e.getId()))
                        .toList();
            } else if (type == 2) {
                return ((HashMap<Integer, DTO>) childObject).values().stream()
                        .filter(e -> idss.contains(e.getId()))
                        .collect(Collectors.toSet());
            }
        }

        StringJoiner joiner = new StringJoiner("_");
        for (Object arg : args) {
            joiner.add(arg.toString());
        }

        var key = joiner.toString();

        //взяли из methods порядковый номер метода
        var result = mapRepos.get(repoIndex).get(methodOrder).get(key);


        System.out.println("repoIndex " + repoIndex + " methodOrder " + methodOrder + " key " + key + " result " + result);


        if (result == null) {
            int type = collectionType.get(repoIndex).get(methodOrder);

            if (type == 0) {
                return save((DTO) joinPoint.proceed(), repoIndex);
            } else if (type == 1) {
                var dtos = (List<DTO>) joinPoint.proceed();
                for (var dto : dtos) {
                    save(dto, repoIndex);
                }
                return dtos;
            } else if (type == 2) {
                var dtos = (Set<DTO>) joinPoint.proceed();
                for (var dto : dtos) {
                    save(dto, repoIndex);
                }
                return dtos;
            }
            return null;
        } else {
            //EntityMapper mapper = (EntityMapper) context.getBean("PersonEntityMapperImpl");
            int type = collectionType.get(repoIndex).get(methodOrder);

            if (type == 0) {
                return result;
            } else if (type == 1) {
                return new ArrayList<DTO>(((HashMap) result).values());
            } else if (type == 2) {
                return new HashSet<DTO>(((HashMap) result).values());
            }
            return null;
            //mapper.toEntity(result);
        }
    }

    @Around("execution(* com.example.jpacachespring.aop.repo.*.save(..))")
    public Object processSave(ProceedingJoinPoint joinPoint)
            throws IllegalAccessException, NoSuchFieldException {

        var repoFile = joinPoint.getThis().getClass().getSimpleName();

        DTO dto = (DTO) joinPoint.getArgs()[0];
        var repoIndex = repos.get(repoFile.substring(0, repoFile.length() - 20));

        try {
            return save(dto, repoIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private DTO save(DTO dto, Integer repoIndex)
            throws IllegalAccessException, NoSuchFieldException {
        //var className = firstLower(obj.getClass().getSimpleName());
        //EntityMapper mapper = (EntityMapper) context.getBean( className + "MapperImpl");
        //var returnClass = mapper.getClass().getMethod("toDto", obj.getClass()).getReturnType();
        //var dto = returnClass.cast(mapper.toDto(obj.getClass().cast(obj)));

        idMap.get(repoIndex).put(dto.getId(), dto);

        //если новый обьект
        if (dto.getFieldValues() == null) {

            var fieldValues = new ArrayList<>();

            var idField = dto.getClass().getSuperclass().getDeclaredField("id");
            idField.setAccessible(true);
            var idValue = idField.get(dto);
            fieldValues.add(idValue);

            int fieldIndex = 0;
            for (var fieldName : dtoFieldNames.get(repoIndex)) {

                var field = dto.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);

                var fieldValue = field.get(dto);
                fieldValues.add(fieldValue);

                var parentDT0Ind = childDTOFieldParentDTOIndex.get(repoIndex).get(fieldIndex);

                if (parentDT0Ind != null) {

                    var set = mapDependant.get(parentDT0Ind).get(repoIndex).get((Integer) fieldValue);

                    if (set == null) {
                        set = new HashSet<>();
                        mapDependant.get(parentDT0Ind).get(repoIndex).put((Integer) fieldValue, set);
                    }
                    set.add(dto.getId());
                }
                fieldIndex++;
            }

            dto.setFieldValues(fieldValues);
            //var dtoFields = dtoFieldNames.get(repoIndex);

            var keys = new ArrayList<String>();
            dtoKeys.get(repoIndex).put(dto.getId(), keys);


            for (int i = 0; i < parameters.get(repoIndex).size(); i++) {

                var parameter = parameters.get(repoIndex).get(i);
                var submap = mapRepos.get(repoIndex).get(i);

                List<String> newKeyArr = new ArrayList<>();//String[parameter.length];
                for (String param : parameter) {
                    if (param != null) {
                        var field = dto.getClass().getDeclaredField(param);
                        field.setAccessible(true);

                        var newFieldValue = field.get(dto);

                        newKeyArr.add(newFieldValue.toString());
                    }
                }

                String newKey = String.join("_", newKeyArr);

                int type = collectionType.get(repoIndex).get(i);
                if (type == 0) {
                    submap.put(newKey, dto);
                } else {

                    var markMap = (HashMap<Integer, DTO>) submap.get(newKey);
                    if (markMap == null) {
                        markMap = new HashMap<>();
                        submap.put(newKey, markMap);
                    }
                    markMap.put(dto.getMark(), dto);

                }
                keys.add(newKey);
            }

        } else {//если изменяем старый обьект
            var dtoFields = dtoFieldNames.get(repoIndex);
            int fieldSize = dtoFields.length;

            boolean[] fieldsChanged = new boolean[fieldSize];
            Object[] previousFields = new Object[fieldSize];

            int newFieldIdValue = dto.getId();

            int oldId = (int) dto.getFieldValues().get(0);

            if (oldId != newFieldIdValue) {

                dtoKeys.get(repoIndex).put(
                        newFieldIdValue,
                        dtoKeys.get(repoIndex).remove(oldId));

                var chDTO = mapDependant.get(repoIndex);

                for (int childInd = 0; childInd < chDTO.size(); childInd++) {

                    Map<Integer, Set<Integer>> parentChildren = chDTO.get(childInd);

                    //все id дочерних -
                    var dependentIds = parentChildren.get(oldId);
                    parentChildren.put(newFieldIdValue, dependentIds);

                    if (dependentIds == null || dependentIds.size() == 0) {
                        continue;
                    }

                    int childOrder = parentDTOChildDTOIndex.get(repoIndex).indexOf(childInd);
                    int fieldIndex = parentDTOChildDTOFieldIndex.get(repoIndex).get(childOrder);
                    String fieldName = dtoFieldNames.get(childInd)[fieldIndex];

                    for (var dependentId : dependentIds) {
                        DTO childDto = idMap.get(childInd).get(dependentId);
                        Field field = childDto.getClass().getDeclaredField(fieldName);
                        field.setAccessible(true);
                        field.set(childDto, newFieldIdValue);
                    }

                    List<Integer> methodList = parentDTOChildMethodIndex.get(repoIndex).get(childInd);
                    List<Integer> paramList = parentDTOChildParamIndex.get(repoIndex).get(childInd);
                    var paramField = parameterDTOFieldInds.get(childInd);
                    var childMap = mapRepos.get(childInd);

                    for (int methodI = 0; methodI < methodList.size(); methodI++) {

                        int methodInd = methodList.get(methodI);
                        int paramInd = paramList.get(methodI);

                        var parameter = paramField.get(methodInd);
                        //значения по методу поиска
                        var childSubmap = childMap.get(methodInd);

                        String[] newKeyArr = new String[parameter.length];
                        String[] oldKeyArr = new String[parameter.length];

                        for (var dependentId : dependentIds) {

                            DTO childDto = idMap.get(childInd).get(dependentId);

                            //метод поиска find
                            for (int j = 0; j < parameter.length; j++) {
                                //порядок в мапе полей значений dto
                                int fieldInd = parameter[j] + 1;
                                //индекс измененного поля
                                oldKeyArr[j] = childDto.getFieldValues().get(fieldInd).toString();
                                if (j == paramInd) {
                                    newKeyArr[j] = Integer.valueOf(newFieldIdValue).toString();
                                } else {
                                    newKeyArr[j] = oldKeyArr[j];
                                }
                            }

                            ((HashMap<Integer, DTO>) childSubmap.get(String.join("_", oldKeyArr))).remove(childDto.getMark());

                            var newKey = String.join("_", newKeyArr);

                            var byKey = (HashMap<Integer, DTO>) childSubmap.get(newKey);
                            if (byKey == null) {
                                byKey = new HashMap<>();
                                childSubmap.put(newKey, byKey);
                            }
                            byKey.put(childDto.getMark(), childDto);
                        }
                    }
                }

                dto.getFieldValues().set(0, newFieldIdValue);
            }

            for (int fieldIndex = 0; fieldIndex < fieldSize; fieldIndex++) {

                var fieldName = dtoFields[fieldIndex];

                var field = dto.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);

                var newFieldValue = field.get(dto);
                var oldValue = dto.getFieldValues().get(fieldIndex + 1);

                //если значение поля изменилось
                if (newFieldValue != null && !newFieldValue.equals(oldValue)
                    || (newFieldValue == null && oldValue != null)) {
                    //идут в паре
                    previousFields[fieldIndex] = oldValue;
                    fieldsChanged[fieldIndex] = true;

                    //обновляем значение поля в dto
                    dto.getFieldValues().set(fieldIndex + 1, newFieldValue);
                    //если поменяли не id
                    //если поменяли в дочернем объекте поле - перекидываем его id в куче дочерних элементов из его
                    // предыдущего родителя в кучу нового

                    var parentDT0 = childDTOFieldParentDTOIndex.get(repoIndex).get(fieldIndex + 1);

                    if (parentDT0 != null) {

                        System.out.println("fieldIndex " + fieldIndex +" fieldName  "+ fieldName + " parentDT0 " + parentDT0 + " repoIndex " + repoIndex
                                + " oldValue " + oldValue + " newFieldValue " + newFieldValue +" dto.getId() " + dto.getId());
                        //parentID
                        mapDependant.get(parentDT0).get(repoIndex).get((Integer) oldValue).remove(dto.getId());
                        mapDependant.get(parentDT0).get(repoIndex).get((Integer) newFieldValue).add(dto.getId());
                    }
                }
                fieldIndex++;
            }

            var keys = dtoKeys.get(repoIndex).get(dto.getId());

            //репозиторий - метод поиска find
            for (int methodInd = 0; methodInd < parameterDTOFieldInds.get(repoIndex).size(); methodInd++) {
                //метод поиска find
                var parameter = parameterDTOFieldInds.get(repoIndex).get(methodInd);
                //значения по методу поиска
                var submap = mapRepos.get(repoIndex).get(methodInd);

                String[] newKeyArr = new String[parameter.length];
                String[] oldKeyArr = new String[parameter.length];

                System.out.println("save old @@@repoIndex " + repoIndex + " methodInd " + methodInd + " " +Arrays.toString(parameter));
                //метод поиска find
                for (int j = 0; j < parameter.length; j++) {
                    //порядок в мапе полей значений dto
                    int fieldInd = parameter[j];
                    //индекс измененного поля

                    newKeyArr[j] = dto.getFieldValues().get(fieldInd + 1).toString();

                    if (fieldsChanged[fieldInd]) {
                        //changedFieldNames идет параллельно с previousFields
                        oldKeyArr[j] = previousFields[fieldInd].toString();
                    } else {
                        oldKeyArr[j] = newKeyArr[j];
                    }
                }

                String newKey = String.join("_", newKeyArr);
                keys.set(methodInd, newKey);

                int type = collectionType.get(repoIndex).get(methodInd);
                if (type == 0) {
                    submap.remove(String.join("_", oldKeyArr), dto);
                    submap.put(newKey, dto);
                } else {
                    var o = (Map<Integer, DTO>) submap.get(String.join("_", oldKeyArr));
                    o.remove(dto.getMark());

                    var n = (Map<Integer, DTO>) submap.get(newKey);
                    if (n == null) {
                        submap.put(newKey, new HashMap<>(Map.of(dto.getMark(), dto)));
                    } else {
                        n.put(dto.getMark(), dto);
                    }
                }
            }
        }

        final String[] repoFieldCollectionNames = dtoFieldCollectionNames.get(repoIndex);

        //System.out.println("dtoFieldCollectionNames " + repoIndex + " " + Arrays.toString(repoFieldCollectionNames));

        for (int i = 0; i < repoFieldCollectionNames.length; i++) {

            var field = dto.getClass().getDeclaredField(repoFieldCollectionNames[i]);
            field.setAccessible(true);

            List<DTO> fieldValues = (List<DTO>) field.get(dto);
            int childDTOIndex = dtoFieldCollectionOrder.get(repoIndex)[i];

            //System.out.println(fieldValues + " @@@++++ " + childDTOIndex);

            for (DTO fieldValue : fieldValues) {
                save(fieldValue, childDTOIndex);
            }
        }

        return dto;
    }

    private String firstLower(String s) {
        char c[] = s.toCharArray();
        c[0] = Character.toLowerCase(c[0]);
        return new String(c);
    }

    private String firstUpper(String s) {
        char c[] = s.toCharArray();
        c[0] = Character.toLowerCase(c[0]);
        return new String(c);
    }

    private void processParentDTO() {
        //мы ищем совпадения названий полей в DTO
        for (int parentDTOInd = 0; parentDTOInd < dtoNames.size(); parentDTOInd++) {

            for (int childDTOInd = 0; childDTOInd < dtoFieldNames.size(); childDTOInd++) {
                for (int childFieldInd = 0; childFieldInd < dtoFieldNames.get(childDTOInd).length; childFieldInd++) {

                    var childFieldName = dtoFieldNames.get(childDTOInd)[childFieldInd];
                    if (childFieldName.startsWith(dtoNames.get(parentDTOInd))) {

                        //остаток названия поля дочернего класса - должен называться как поле родителя
                        var childFieldNameRest = childFieldName.substring(dtoNames.get(parentDTOInd).length());
                        //если правда называется так - значит дочерний - ищем индекс этого поля
                        if (childFieldNameRest.equals("Id")) {
                            parentDTOChildDTOIndex.get(parentDTOInd).add(childDTOInd);
                            parentDTOChildDTOFieldIndex.get(parentDTOInd).add(childFieldInd);

                            childDTOFieldParentDTOIndex.get(childDTOInd).set(childFieldInd, parentDTOInd);
                            //просто список методов, где используется поле
                            //                                 дочерний dto     зависимое поле
                            int size = DTOFieldMethodIndex.get(childDTOInd).get(childFieldInd).size();
                            for (int y = 0; y < size; y++) {
                                int childMethod = DTOFieldMethodIndex.get(childDTOInd).get(childFieldInd).get(y);
                                int childParam = DTOFieldParamIndex.get(childDTOInd).get(childFieldInd).get(y);
                                //а это список методов для дочернего dto, где используется ссылка на поле
                                parentDTOChildMethodIndex.get(parentDTOInd).get(childDTOInd)
                                        .add(childMethod);
                                //а это список параметров - парных для мтеодов для дочернего dto,
                                // где используется ссылка на поле
                                parentDTOChildParamIndex.get(parentDTOInd).get(childDTOInd)
                                        .add(childParam);
                            }
                        }
                    }
                }
            }
        }
    }

}